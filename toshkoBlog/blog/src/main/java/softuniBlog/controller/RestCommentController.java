package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import softuniBlog.entity.Comment;
import softuniBlog.repository.ArticleRepository;

import java.util.List;

/**
 * Created by todor on 18.8.2017 г..
 */
@RestController
public class RestCommentController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/article/{articleId}/comments")
    public List<Comment> showComments(@PathVariable Integer articleId){
        return this.articleRepository.findOne(articleId).getComments();
    }
}
