package softuniBlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuniBlog.entity.Article;
import softuniBlog.entity.Like;
import softuniBlog.entity.User;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Integer> {
    Integer countAllByArticleId(Integer id);

    Like findByArticleAndUser(Article article, User userEntity);

    List<Like> findAllByArticleAndLikedTrue(Article article);
}
