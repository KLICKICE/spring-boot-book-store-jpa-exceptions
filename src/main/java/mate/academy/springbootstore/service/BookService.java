package mate.academy.springbootstore.service;

import java.util.List;
import mate.academy.springbootstore.dto.BookDto;
import mate.academy.springbootstore.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    List<BookDto> getAll(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto createBook(CreateBookRequestDto bookDto);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto bookDto);
}
