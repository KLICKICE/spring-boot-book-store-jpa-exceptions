package mate.academy.springbootstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import mate.academy.springbootstore.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("""
            Save a book to DB, success 
            """)
    void saveBook_success() {
        Book book = new Book();
        book.setAuthor("Sunless");
        book.setTitle("Lost from Light");
        book.setIsbn("123-456-789");
        book.setPrice(BigDecimal.valueOf(999));
        book.setDescription("Night book");

        Book savedBook = bookRepository.save(book);

        assertNotNull(savedBook.getId());
        assertEquals("Sunless", savedBook.getAuthor());
        assertEquals("Lost from Light", savedBook.getTitle());
        assertEquals("123-456-789", savedBook.getIsbn());
        assertEquals(BigDecimal.valueOf(999), savedBook.getPrice());
        assertEquals("Night book", savedBook.getDescription());
    }

    @Test
    @DisplayName("""
            Find book by id success
            """)
    void findBookById_success() {
        Book book = new Book();
        book.setAuthor("Sunless");
        book.setTitle("Lost from Light");
        book.setIsbn("123-456-789");
        book.setPrice(BigDecimal.valueOf(999));
        book.setDescription("Night book");

        Book savedBook = bookRepository.save(book);

        assertTrue(bookRepository.findById(savedBook.getId()).isPresent());
    }

    @Test
    @DisplayName("""
            Find book by id and then do soft delete
            """)
    void deleteBookWithSoftDelete_success() {
        Book book = new Book();
        book.setAuthor("Sunless");
        book.setTitle("Lost from Light");
        book.setIsbn("123-456-789");
        book.setPrice(BigDecimal.valueOf(999));
        book.setDescription("Night book");

        Book savedBook = bookRepository.save(book);

        assertTrue(bookRepository.findById(savedBook.getId()).isPresent());

        bookRepository.deleteById(savedBook.getId());

        assertTrue(bookRepository.findById(savedBook.getId()).isEmpty());
    }

    @Test
    @DisplayName("Save book without mandatory fields should throw exception")
    void saveBook_missingMandatoryFields_throwsException() {
        Book book = new Book();
        assertThrows(Exception.class, () -> bookRepository.saveAndFlush(book));
    }

    @Test
    @DisplayName("Save two books with same ISBN should throw exception")
    void saveBooksWithSameIsbn_throwsException() {
        Book book1 = new Book();
        book1.setAuthor("A");
        book1.setTitle("Book A");
        book1.setIsbn("111");
        book1.setPrice(BigDecimal.TEN);

        Book book2 = new Book();
        book2.setAuthor("B");
        book2.setTitle("Book B");
        book2.setIsbn("111");
        book2.setPrice(BigDecimal.ONE);

        bookRepository.saveAndFlush(book1);
        assertThrows(Exception.class, () -> bookRepository.saveAndFlush(book2));
    }
}
