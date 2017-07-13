package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.User;
import softuniBlog.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register (Model model){

       model.addAttribute("view","user/register");
       return "base-layout";
    }

    @PostMapping("/register")
    public String registerProcess (UserBindingModel userBindingModel) throws IOException {
        this.userService.registerUser(userBindingModel);

        return "redirect:/login";

    }

    @GetMapping("/login")
    public String login (Model model){
        model.addAttribute("view","user/login");

        return "base-layout";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        this.userService.logout(request,response);

        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage (Model model){

        User user = this.userService.getUserPrincipal();

        model.addAttribute("user",user);
        model.addAttribute("encodeToString", Base64.getEncoder().encodeToString(user.getPicture()));
        model.addAttribute("view","user/profile");
        return "base-layout";
    }

    @GetMapping("/user/{id}/articles")
    public String listArticlesByUser (@PathVariable Integer id, Model model){
        List<Article> articles = this.userService.getAllArticlesByAuthorId(id);
        model.addAttribute("articles", articles);
        model.addAttribute("view", "/user/listArticles");
        return "base-layout";
    }

}
