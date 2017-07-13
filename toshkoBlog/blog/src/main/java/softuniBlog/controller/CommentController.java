package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.CommentBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Comment;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CommentRepository;
import softuniBlog.repository.UserRepository;
import softuniBlog.service.CommentService;
import java.util.List;

@Controller
public class CommentController {

//    private final CommentService commentService;
//    private final ArticleRepository articleRepository;
//    private final UserRepository userRepository;
//    private final CommentRepository commentRepository;
//
//
//    @Autowired
//    public CommentController(CommentRepository commentRepository,CommentService commentService, ArticleRepository articleRepository, UserRepository userRepository) {
//        this.commentService = commentService;
//        this.articleRepository = articleRepository;
//        this.userRepository = userRepository;
//        this.commentRepository = commentRepository;
//    }


//    @GetMapping("/loadComments/{numberOfLastComments}/{articleId}")
//    public List<Comment> loadComments (@PathVariable("numberOfLastComments") Integer numberOfLastComments,
//                                       @PathVariable("articleId") Integer articleId){
//        return this.commentService.loadLastComments(numberOfLastComments,articleId);
//    }
//
//    @PostMapping("/comment/create")
//    public Integer postCommentProcess(@RequestParam(value = "commentContent", required=false)
//                                                 String commentContent, @RequestParam(value = "articleId",required=false) String articleId){
//        Article article = this.articleRepository.findOne(Integer.parseInt(articleId));
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();
//        User user = this.userRepository.findByEmail(email);
//
//        this.commentService.createComment(commentContent, user, article);
//         Integer commentCount = this.commentRepository.findAllByArticle_IdOrderByIdAsc(article.getId()).size();
//        return commentCount;
//    }


//    private final CommentService commentService;
//
//    @Autowired
//    public CommentController(CommentService commentService) {
//        this.commentService = commentService;
//    }
//
//   @GetMapping("/article/details/{id}")
//   @PreAuthorize("isAuthenticated()")
//   public String addComment (@PathVariable Integer id, Model model)
//   {
//
//       List<Comment> comments = this.commentService.getCommentsByArticleId(id);
//
//
//       model.addAttribute("comments",comments);
//       model.addAttribute("view", "article/details");
//
//       return "base-layout";
//
//   }
//
//   @PostMapping("/article/details/{id}")
//   @PreAuthorize("isAuthenticated()")
//   public String addCommentProcess (@PathVariable Integer id, CommentBindingModel commentBindingModel){
//
//       this.commentService.addCommentToArticleById(id, commentBindingModel);
//
//       return "redirect:/article/" + id;
//   }


}
