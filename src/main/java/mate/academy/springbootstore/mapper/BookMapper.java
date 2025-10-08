package mate.academy.springbootstore.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.springbootstore.config.MapConfig;
import mate.academy.springbootstore.dto.book.BookDto;
import mate.academy.springbootstore.dto.book.CreateBookRequestDto;
import mate.academy.springbootstore.dto.category.CategoryDto;
import mate.academy.springbootstore.model.Book;
import mate.academy.springbootstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapConfig.class)
public interface BookMapper {

    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateBookFromDto(CreateBookRequestDto book, @MappingTarget Book entity);

    default List<CategoryDto> mapCategories(Set<Category> categories) {
        if (categories == null) {
            return Collections.emptyList();
        }
        return categories.stream()
                .map(c -> new CategoryDto(c.getId(), c.getName(), null))
                .collect(Collectors.toList());
    }
}

