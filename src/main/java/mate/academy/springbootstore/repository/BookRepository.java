package mate.academy.springbootstore.repository;

import mate.academy.springbootstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
