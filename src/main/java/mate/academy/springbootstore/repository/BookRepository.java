package mate.academy.springbootstore.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.springbootstore.model.Book;


public interface BookRepository {
    List<Book> getAll();

    Optional<Book> getBookById(Long id);

    Book createBook(Book book);
}
