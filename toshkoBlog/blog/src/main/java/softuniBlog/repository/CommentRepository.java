package softuniBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.Article;
import softuniBlog.entity.Comment;

import java.util.List;

/**
 * Created by todor on 20.7.2017 г..
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByArticle_IdOrderByIdAsc(Integer articleId);


}