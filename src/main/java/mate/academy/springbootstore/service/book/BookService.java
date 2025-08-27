package mate.academy.springbootstore.service.book;

import mate.academy.springbootstore.dto.book.BookDto;
import mate.academy.springbootstore.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<BookDto> getAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto bookDto);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto bookDto);
}
