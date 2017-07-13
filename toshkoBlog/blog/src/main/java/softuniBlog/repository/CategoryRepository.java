package softuniBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.Category;

/**
 * Created by todor on 13.7.2017 г..
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
