package blogsystem.controler;

import blogsystem.bindingModel.ArticleBindingModel;
import blogsystem.entity.Article;
import blogsystem.entity.User;
import blogsystem.repository.ArticleRepository;
import blogsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by yovo on 12-07-2017.
 */
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model){
        model.addAttribute("view","article/create");
        return "base-layout";
    }

    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());
        Article articleEntity = new Article(
                articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity
        );
        this.articleRepository.saveAndFlush(articleEntity);
        // we want to redirect our user to the home page of our blog. We will use the "redirect:" syntax
        return "redirect:/";
    }

//    Something new! In our route, we declare parameter
//    using curly brackets. Then in our method we use the
//    "@PathVariable" annotation to tell Spring that this
//    parameter should be taken from the URL. We are now free to use it in our method.
    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id){
        if(!(SecurityContextHolder.getContext()
                .getAuthentication()instanceof AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            User entityUser = this.userRepository.findByEmail(principal.getUsername());
            model.addAttribute("user",entityUser);
        }
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }
        Article article;
        article = this.articleRepository.findOne(id);

        model.addAttribute("article",article);
        model.addAttribute("view","article/details");
        return "base-layout";
    }

    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id,Model model){
        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.getOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        model.addAttribute("view","article/edit");
        model.addAttribute("article",article);
        return "base-layout";
    }


    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id,
                              ArticleBindingModel articleBindingModel){
        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }
     Article article;
        article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }

        article.setContent(articleBindingModel.getContent());
        article.setTitle(articleBindingModel.getTitle());
        this.articleRepository.saveAndFlush(article);

        return "redirect:/article/"+article.getId();

    }
    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model,@PathVariable Integer id){
        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);

        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }
        model.addAttribute("article",article);
        model.addAttribute("view","article/delete");
        return "base-layout";
    }

    @PostMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id ){
        if(!this.articleRepository.exists(id)){
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);


        if(!isUserAuthorOrAdmin(article)){
            return "redirect:/article/" + id;
        }
        this.articleRepository.delete(article);
        return "redirect:/";

    }
    private boolean isUserAuthorOrAdmin(Article article){
       UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       User userEntity = this.userRepository.findByEmail(user.getUsername());

       return userEntity.isAdmin() || userEntity.isAuthor(article);
    }
}
