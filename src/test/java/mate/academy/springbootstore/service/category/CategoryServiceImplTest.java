package mate.academy.springbootstore.service.category;

import mate.academy.springbootstore.dto.category.CategoryDto;
import mate.academy.springbootstore.dto.category.CreateCategoryRequestDto;
import mate.academy.springbootstore.mapper.CategoryMapper;
import mate.academy.springbootstore.model.Category;
import mate.academy.springbootstore.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    @DisplayName("""
            Get category by id, success
            """)
    void getCategory_ById_success() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        category.setName("Fiction");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName("Fiction");

        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.getCategoryById(id);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Fiction", actual.getName());
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(id);
        Mockito.verify(categoryMapper, Mockito.times(1)).toDto(category);
    }

    @Test
    @DisplayName("""
        Delete category by id, success
        """)
    void deleteCategory_ById_success() {
        Long id = 1L;

        Mockito.when(categoryRepository.existsById(id)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> categoryService.deleteById(id));

        Mockito.verify(categoryRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("""
        Create category, success
        """)
    void createCategory_success() {
        Long id = 1L;

        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto();
        categoryRequestDto.setName("Fiction");
        categoryRequestDto.setDescription("Fantastic, category");

        Category category = new Category();

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName("Fiction");

        Mockito.when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.createCategory(categoryRequestDto);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Fiction", actual.getName());

        Mockito.verify(categoryMapper).toModel(categoryRequestDto);
        Mockito.verify(categoryRepository).save(category);
        Mockito.verify(categoryMapper).toDto(category);
    }
}