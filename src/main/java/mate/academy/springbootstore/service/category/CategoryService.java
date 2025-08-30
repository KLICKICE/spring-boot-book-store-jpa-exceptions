package mate.academy.springbootstore.service.category;

import mate.academy.springbootstore.dto.category.CategoryDto;
import mate.academy.springbootstore.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryDto> getAll(Pageable pageable);

    CategoryDto getCategoryById(Long id);

    CategoryDto createCategory(CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);
}

