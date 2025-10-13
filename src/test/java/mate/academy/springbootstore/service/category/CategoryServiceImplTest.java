package mate.academy.springbootstore.service.category;

import mate.academy.springbootstore.dto.category.CategoryDto;
import mate.academy.springbootstore.dto.category.CreateCategoryRequestDto;
import mate.academy.springbootstore.mapper.CategoryMapper;
import mate.academy.springbootstore.model.Category;
import mate.academy.springbootstore.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Get category by id, success")
    void getCategory_ById_success() {
        // given
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        category.setName("Fiction");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName("Fiction");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // when
        CategoryDto actual = categoryService.getCategoryById(id);

        // then
        assertNotNull(actual);
        assertEquals("Fiction", actual.getName());
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    @DisplayName("Delete category by id, success")
    void deleteCategory_ById_success() {
        // given
        Long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(true);

        // when / then
        assertDoesNotThrow(() -> categoryService.deleteById(id));
        verify(categoryRepository, times(1)).existsById(id);
        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Create category, success")
    void createCategory_success() {
        // given
        Long id = 1L;

        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Fiction");
        requestDto.setDescription("Fantastic, category");

        Category category = new Category();

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName("Fiction");

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // when
        CategoryDto actual = categoryService.createCategory(requestDto);

        // then
        assertNotNull(actual);
        assertEquals("Fiction", actual.getName());
        verify(categoryMapper).toModel(requestDto);
        verify(categoryRepository).save(category);
        verify(categoryMapper).toDto(category);
    }
}
