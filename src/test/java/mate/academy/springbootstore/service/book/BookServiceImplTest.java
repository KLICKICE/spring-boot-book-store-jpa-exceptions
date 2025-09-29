package mate.academy.springbootstore.service.book;

import mate.academy.springbootstore.dto.book.BookDto;
import mate.academy.springbootstore.dto.book.CreateBookRequestDto;
import mate.academy.springbootstore.mapper.BookMapper;
import mate.academy.springbootstore.model.Book;
import mate.academy.springbootstore.model.Category;
import mate.academy.springbootstore.repository.BookRepository;
import mate.academy.springbootstore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.getBookById(id);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Lost from Light", actual.getTitle());
        Mockito.verify(bookRepository, Mockito.times(1)).findById(id);
        Mockito.verify(bookMapper, Mockito.times(1)).toDto(book);
    }

    @Test
    @DisplayName("""
        Delete book by id, success
        """)
    void deleteBook_ById_success() {
        Long id = 1L;

        Mockito.when(bookRepository.existsById(id)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> bookService.deleteById(id));

        Mockito.verify(bookRepository).existsById(id);
        Mockito.verify(bookRepository).deleteById(id);
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

        Mockito.when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);
        Mockito.when(categoryRepository.findAllById(createBookRequestDto.getCategoryIds()))
                .thenReturn(categories);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.createBook(createBookRequestDto);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Lost from Light", actual.getTitle());

        Mockito.verify(bookRepository).save(book);
        Mockito.verify(categoryRepository).findAllById(createBookRequestDto.getCategoryIds());
        Mockito.verify(bookMapper).toDto(book);
    }

}