package mate.academy.springbootstore.repository;

import static org.junit.jupiter.api.Assertions.*;

import mate.academy.springbootstore.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("""
            Save a category to DB, success
            """)
    void saveCategory_success() {
        Category category = new Category();
        category.setName("Black life");
        category.setDescription("Category about shadows");

        Category savedCategory = categoryRepository.save(category);

        assertNotNull(savedCategory.getId());
        assertEquals("Black life", savedCategory.getName());
        assertEquals("Category about shadows", savedCategory.getDescription());
    }

    @Test
    @DisplayName("""
            Find category by id
            """)
    void findCategory_byId_success() {
        Category category = new Category();
        category.setName("Black life");
        category.setDescription("Category about shadows");

        Category savedCategory = categoryRepository.save(category);

        assertTrue(categoryRepository.findById(savedCategory.getId()).isPresent());
    }

    @Test
    @DisplayName("""
            Delete category by id
            """)
    void deleteCategory_byId_success() {
        Category category = new Category();
        category.setName("Black life");
        category.setDescription("Category about shadows");

        Category savedCategory = categoryRepository.save(category);

        assertTrue(categoryRepository.findById(savedCategory.getId()).isPresent());

        categoryRepository.deleteById(savedCategory.getId());

        assertTrue(categoryRepository.findById(savedCategory.getId()).isEmpty());
    }

    @Test
    @DisplayName("""
            Save category without mandatory fields, should throw Exception
            """)
    void saveCategory_withoutMandatoryFields_failure() {
        Category category = new Category();

        assertThrows(Exception.class, () -> categoryRepository.saveAndFlush(category));
    }
}
