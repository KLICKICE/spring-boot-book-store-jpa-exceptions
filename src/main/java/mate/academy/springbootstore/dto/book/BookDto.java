package mate.academy.springbootstore.dto.book;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import mate.academy.springbootstore.dto.category.CategoryDto;

@Data
public class BookDto {
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private BigDecimal price;

    private String description;

    private String coverImage;

    private List<CategoryDto> categories;
}
