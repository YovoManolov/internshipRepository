package softuniBlog.service;

import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.bindingModel.CommentBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Comment;
import softuniBlog.entity.User;

import java.util.List;


public interface CommentService {
    List<Comment> getCommentsByArticleId (@PathVariable Integer id);
    void addCommentToArticleById (@PathVariable Integer id, CommentBindingModel commentBindingModel);
    List<Comment> loadLastComments(Integer numberOfLastComments, Integer articleId);
    void createComment(String content, User user, Article article );

}

