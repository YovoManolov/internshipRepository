package softuniBlog.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.entity.Tag;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findAllByCategory(Category category, Pageable pageable);
    Integer countAllByCategory(Category category);
    List<Article> findAllByAuthor_Id(Integer id);




}
