package blogsystem.repository;

import blogsystem.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yovo on 12-07-2017.
 */
public interface ArticleRepository extends JpaRepository<Article,Integer> {

}
