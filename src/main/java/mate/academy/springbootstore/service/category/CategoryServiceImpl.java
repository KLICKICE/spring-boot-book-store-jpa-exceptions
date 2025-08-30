package mate.academy.springbootstore.service.category;

import lombok.RequiredArgsConstructor;
import mate.academy.springbootstore.dto.category.CategoryDto;
import mate.academy.springbootstore.dto.category.CreateCategoryRequestDto;
import mate.academy.springbootstore.exception.EntityNotFoundException;
import mate.academy.springbootstore.mapper.CategoryMapper;
import mate.academy.springbootstore.model.Category;
import mate.academy.springbootstore.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                "Failed to get category: category with id " + id + " not found"));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto createCategory(CreateCategoryRequestDto categoryDto) {
        Category categorySaved = categoryMapper.toModel(categoryDto);
        categoryRepository.save(categorySaved);
        return categoryMapper.toDto(categorySaved);
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Failed to delete category: category with id "
                    + id + " not found");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto categoryDto) {
        Category categoryFromDb = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                "Failed to update category: category with id " + id + " not found"));
        categoryMapper.updateCategoryFromDto(categoryDto, categoryFromDb);
        categoryRepository.save(categoryFromDb);
        return categoryMapper.toDto(categoryFromDb);
    }
}
