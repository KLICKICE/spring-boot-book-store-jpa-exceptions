package mate.academy.springbootstore.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.BookDto;
import mate.academy.springbootstore.dto.CreateBookRequestDto;
import mate.academy.springbootstore.service.BookService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto bookDto) {
        return bookService.createBook(bookDto);
    }
}
