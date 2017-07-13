package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import softuniBlog.entity.Article;
import softuniBlog.entity.Like;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.LikeRepository;
import softuniBlog.repository.UserRepository;
import java.util.LinkedList;
import java.util.List;

@RestController
public class RestLikeController {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Autowired
    public RestLikeController(ArticleRepository articleRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @GetMapping("/article/{articleId}/likes")
    @PreAuthorize("isAuthenticated()")
    public List<User> getArticle (@PathVariable("articleId") Integer articleId){
        Article article = this.articleRepository.getOne(articleId);

        List<Like> usersLikedList = this.likeRepository.findAllByArticleAndLikedTrue(article);

        List<User> userLinkedList = new LinkedList<>();

        for(Like liked : usersLikedList){
            userLinkedList.add(liked.getUser());
        }
        return userLinkedList;
    }

    @GetMapping("/article/{articleId}/likeCount")
    public Integer showLikes(@PathVariable Integer articleId){
        Article article = this.articleRepository.getOne(articleId);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User userEntity = this.userRepository.findByEmail(principal.getUsername());
        Like like = this.likeRepository.findByArticleAndUser(article, userEntity);
        if(like==null) {
            like = new Like(article, userEntity, true);
        }
        else if(like.getLiked()) {
            like.setLiked(false);
        }
        else {
            like.setLiked(true);
        }
        this.likeRepository.saveAndFlush(like);

        return article.getLikesCount();
    }

}

