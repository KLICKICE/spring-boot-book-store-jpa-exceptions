package mate.academy.springbootstore.repository;

import mate.academy.springbootstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
