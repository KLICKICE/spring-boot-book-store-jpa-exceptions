package mate.academy.springbootstore.mapper;

import mate.academy.springbootstore.config.MapperConfig;
import mate.academy.springbootstore.dto.BookDto;
import mate.academy.springbootstore.dto.CreateBookRequestDto;
import mate.academy.springbootstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
