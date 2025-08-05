package mate.academy.springbootstore.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.BookDto;
import mate.academy.springbootstore.dto.CreateBookRequestDto;
import mate.academy.springbootstore.exception.EntityNotFoundException;
import mate.academy.springbootstore.mapper.BookMapper;
import mate.academy.springbootstore.model.Book;
import mate.academy.springbootstore.repository.BookRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by Id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto createBook(CreateBookRequestDto requestDto) {
        Book savedBook = bookMapper.toModel(requestDto);
        bookRepository.save(savedBook);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't delete book. Id not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(BookDto bookDto) {
        Long id = bookDto.getId();
        if (id == null) {
            throw new IllegalArgumentException("Book ID must not be null for update");
        }

        Book bookFromDb = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't update book. Id not found: " + id));

        bookFromDb.setTitle(bookDto.getTitle());
        bookFromDb.setAuthor(bookDto.getAuthor());
        bookFromDb.setPrice(bookDto.getPrice());
        bookFromDb.setIsbn(bookDto.getIsbn());
        bookFromDb.setDescription(bookDto.getDescription());
        bookFromDb.setCoverImage(bookDto.getCoverImage());


        Book updatedBook = bookRepository.save(bookFromDb);
        return bookMapper.toDto(updatedBook);
    }
}
