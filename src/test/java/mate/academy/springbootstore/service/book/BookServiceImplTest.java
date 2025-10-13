package mate.academy.springbootstore.service.book;

import mate.academy.springbootstore.dto.book.BookDto;
import mate.academy.springbootstore.dto.book.CreateBookRequestDto;
import mate.academy.springbootstore.mapper.BookMapper;
import mate.academy.springbootstore.model.Book;
import mate.academy.springbootstore.model.Category;
import mate.academy.springbootstore.repository.BookRepository;
import mate.academy.springbootstore.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("""
        Get book by id, success
        """)
    void getBook_ById_success() {
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTitle("Lost from Light");

        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle("Lost from Light");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.getBookById(id);

        assertNotNull(actual);
        assertEquals("Lost from Light", actual.getTitle());
        verify(bookRepository, times(1)).findById(id);
        verify(bookMapper, times(1)).toDto(book);
    }

    @Test
    @DisplayName("""
        Delete book by id, success
        """)
    void deleteBook_ById_success() {
        Long id = 1L;

        when(bookRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> bookService.deleteById(id));

        verify(bookRepository).existsById(id);
        verify(bookRepository).deleteById(id);
    }

    @Test
    @DisplayName("""
        Create a book, success
        """)
    void createBook_success() {
        Long id = 1L;

        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setAuthor("Sunless");
        createBookRequestDto.setTitle("Lost from Light");
        createBookRequestDto.setIsbn("123-456-789");
        createBookRequestDto.setPrice(BigDecimal.valueOf(999));
        createBookRequestDto.setDescription("Night book");
        createBookRequestDto.setCategoryIds(List.of(1L, 2L));

        Book book = new Book();

        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle("Lost from Light");

        List<Category> categories = List.of(new Category(), new Category());

        when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);
        when(categoryRepository.findAllById(createBookRequestDto.getCategoryIds())).thenReturn(categories);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.createBook(createBookRequestDto);

        assertNotNull(actual);
        assertEquals("Lost from Light", actual.getTitle());

        verify(bookRepository).save(book);
        verify(categoryRepository).findAllById(createBookRequestDto.getCategoryIds());
        verify(bookMapper).toDto(book);
    }
}
