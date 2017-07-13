package softuniBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuniBlog.entity.Article;
import softuniBlog.entity.Like;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.LikeRepository;
import softuniBlog.repository.UserRepository;
import softuniBlog.service.ArticleService;
import softuniBlog.service.LikeService;

import java.util.List;

@Controller
public class LikeController {

//    private final ArticleService articleService;
//    private final LikeService likeService;
//
//
//
//    @Autowired
//    public LikeController(ArticleService articleService, LikeService likeService) {
//        this.articleService = articleService;
//        this.likeService = likeService;
//    }
//
//
//    @GetMapping("/likes/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public String like(@PathVariable Integer id, Model model) {
//
//        Article article = this.articleService.getArticleById(id);
//
//        if (article == null){
//            return "redirect:/";
//        }
//
//        model.addAttribute("article",article);
//        model.addAttribute("view","article/details");
//
//
//        return "base-layout";
//
//    }
//
//
//    @PostMapping("/likes/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public String likeProcess (@PathVariable Integer id){
//
//        this.likeService.likeArticleById(id);
//        return "redirect:/article/" + id;
//    }
}
