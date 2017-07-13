package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.entity.*;
import softuniBlog.service.ArticleService;
import java.io.IOException;
import java.util.List;


@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {

        List<Category> categories = this.articleService.getCategoryList();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "article/create");

        return "base-layout";

    }

    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel) throws IOException {

        this.articleService.createProcess(articleBindingModel);

        return "redirect:/";

    }

    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id) {

        User user = this.articleService.getUserEntity();
        Article article = this.articleService.getArticleById(id);
        String encodeToString = this.articleService.getEncodedStringFromArticle(article);

        if (user != null){
            model.addAttribute("user", user);
        }

        if (article == null){
            return "redirect:/";
        }

        model.addAttribute("encodeToString", encodeToString);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/details");

        return "base-layout";
    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {

        Article article = this.articleService.getArticleById(id);

        if (article == null){
            return "redirect:/";
        }


        if (!this.articleService.isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;

        }


        List<Category> categories = this.articleService.getCategoryList();
        String tagString = this.articleService.getTagStringFromArticle(article);


        model.addAttribute("view", "article/edit");
        model.addAttribute("article", article);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tagString);


        return "base-layout";
    }


    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, ArticleBindingModel articleBindingModel) {

        Article article = this.articleService.getArticleById(id);

        if (article == null){
            return "redirect:/";
        }

        if (!this.articleService.isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;

        }

        this.articleService.editArticle(article,articleBindingModel);
        return "redirect:/article/" + article.getId();
    }

    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {

        Article article = this.articleService.getArticleById(id);

        if (article == null){
            return "redirect:/";
        }

        if (!this.articleService.isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;

        }

        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");

        return "base-layout";
    }

    @PostMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {

        Article article = this.articleService.getArticleById(id);

        if (article == null){
            return "redirect:/";
        }

        if (!this.articleService.isUserAuthorOrAdmin(article)) {
            return "redirect:/article/" + id;
        }

        this.articleService.deleteArticle(article);
        return "redirect:/";
    }
}
