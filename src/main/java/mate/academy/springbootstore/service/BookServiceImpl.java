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
                .orElseThrow(() -> new EntityNotFoundException(
                        "Failed to get book: book with id " + id + " not found"));
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
            throw new EntityNotFoundException("Failed to delete book: book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto bookDto) {
        Book bookFromDb = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Failed to update book: book with id " + id + " not found"));
        bookMapper.updateBookFromDto(bookDto, bookFromDb);
        bookRepository.save(bookFromDb);
        return bookMapper.toDto(bookFromDb);
    }
}
