package mate.academy.springbootstore.repository;

import mate.academy.springbootstore.model.Category;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertNotNull(savedCategory.getId());
        Assertions.assertEquals("Black life", savedCategory.getName());
        Assertions.assertEquals("Category about shadows", savedCategory.getDescription());
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
        Assertions.assertTrue(categoryRepository.findById(savedCategory.getId()).isPresent());
    }

    @Test
    @DisplayName("""
            Delete category by id
            """)
    void deleteCategory_byId_success () {
        Category category = new Category();
        category.setName("Black life");
        category.setDescription("Category about shadows");
        Category savedCategory = categoryRepository.save(category);
        Assertions.assertTrue(categoryRepository.findById(savedCategory.getId()).isPresent());
        categoryRepository.deleteById(savedCategory.getId());
        Assertions.assertTrue(categoryRepository.findById(savedCategory.getId()).isEmpty());
    }

    @Test
    @DisplayName("""
            Save category without mandatory fields, should throw Exception
            """)
    void saveCategory_withoutMandatoryFields_failure() {
        Category category = new Category();
        Assertions.assertThrows(Exception.class, () -> categoryRepository.saveAndFlush(category));
    }
}
