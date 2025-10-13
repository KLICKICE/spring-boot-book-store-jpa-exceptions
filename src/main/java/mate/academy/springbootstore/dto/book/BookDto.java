package mate.academy.springbootstore.dto.book;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mate.academy.springbootstore.dto.category.CategoryDto;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookDto {
    private Long id;

    @EqualsAndHashCode.Include
    private String title;

    @EqualsAndHashCode.Include
    private String author;

    @EqualsAndHashCode.Include
    private String isbn;

    @EqualsAndHashCode.Include
    private BigDecimal price;

    @EqualsAndHashCode.Include
    private String description;

    @EqualsAndHashCode.Include
    private String coverImage;

    private List<CategoryDto> categories;
}
