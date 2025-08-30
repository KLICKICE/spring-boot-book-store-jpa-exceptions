package mate.academy.springbootstore.mapper;

import mate.academy.springbootstore.config.MapConfig;
import mate.academy.springbootstore.dto.category.CategoryDto;
import mate.academy.springbootstore.dto.category.CreateCategoryRequestDto;
import mate.academy.springbootstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto requestDto);

    void updateCategoryFromDto(CreateCategoryRequestDto category, @MappingTarget Category entity);
}
