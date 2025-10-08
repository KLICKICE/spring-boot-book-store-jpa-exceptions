package mate.academy.springbootstore.service.book;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.book.BookDto;
import mate.academy.springbootstore.dto.book.CreateBookRequestDto;
import mate.academy.springbootstore.exception.EntityNotFoundException;
import mate.academy.springbootstore.mapper.BookMapper;
import mate.academy.springbootstore.model.Book;
import mate.academy.springbootstore.model.Category;
import mate.academy.springbootstore.repository.BookRepository;
import mate.academy.springbootstore.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    private final BookMapper bookMapper;

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Failed to get book: book with id " + id + " not found"));
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional
    public BookDto createBook(CreateBookRequestDto requestDto) {
        Book savedBook = bookMapper.toModel(requestDto);
        Set<Category> categories = getCategoriesByIds(requestDto.getCategoryIds());
        savedBook.setCategories(categories);
        bookRepository.save(savedBook);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Failed to delete book: book with id "
                    + id + " not found");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto bookDto) {
        Book bookFromDb = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Failed to update book: book with id " + id + " not found"));
        bookMapper.updateBookFromDto(bookDto, bookFromDb);
        Set<Category> categories = getCategoriesByIds(bookDto.getCategoryIds());
        bookFromDb.setCategories(categories);
        bookRepository.save(bookFromDb);
        return bookMapper.toDto(bookFromDb);
    }

    private Set<Category> getCategoriesByIds(List<Long> categoryIds) {
        return new HashSet<>(categoryRepository.findAllById(categoryIds));
    }
}
