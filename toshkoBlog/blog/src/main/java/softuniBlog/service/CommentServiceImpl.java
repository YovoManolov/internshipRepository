package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.bindingModel.CommentBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Comment;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CommentRepository;
import softuniBlog.repository.UserRepository;

import java.util.List;

/**
 * Created by todor on 2.8.2017 г..
 */

@Service
public class CommentServiceImpl implements CommentService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ArticleRepository articleRepository, UserRepository userRepository,CommentRepository commentRepository ) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentsByArticleId(Integer id) {
        return this.articleRepository.findOne(id).getComments();
    }

    @Override
    public void addCommentToArticleById(Integer id, CommentBindingModel commentBindingModel) {
        UserDetails user = (UserDetails)
                SecurityContextHolder.
                        getContext().
                        getAuthentication().
                        getPrincipal();


        User entityUser = this.userRepository.findByEmail(user.getUsername());

        Article article = this.articleRepository.findOne(id);
        String content = commentBindingModel.getContent();

        Comment comment = new Comment(entityUser,article, content);
        this.commentRepository.saveAndFlush(comment);
    }

    @Override
    public List<Comment> loadLastComments(Integer numberOfLastComments, Integer articleId) {

        List<Comment> fullListComments = this.commentRepository.findAllByArticle_IdOrderByIdAsc(articleId);
        if(numberOfLastComments>fullListComments.size()){
            throw new IllegalArgumentException("Number of comments should be less or equal ot the total comments");
        }

        return fullListComments.subList(Math.max(fullListComments.size() - numberOfLastComments, 0), fullListComments.size());
    }

    @Override
    public void createComment(String commentContent, User user, Article article) {
        Comment comment = new Comment(
                user,
                article,
                commentContent
        );

        this.commentRepository.saveAndFlush(comment);
    }
}
