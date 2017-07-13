package softuniBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CategoryRepository;
import softuniBlog.repository.UserRepository;
import softuniBlog.service.ArticleService;
import softuniBlog.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String index (Model model){

        List<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("view","home/index");
        model.addAttribute("categories", categories);

        return "base-layout";

    }

    @RequestMapping("/error/403")
    public String accessDenied (Model model){
        model.addAttribute("view","/error/403");

        return "base-layout";
    }

    @GetMapping("/category/{id}")
    public String listArticles (Model model, @PathVariable Integer id){

        if (!this.categoryRepository.exists(id)){
            return "redirect:/";
        }

        Category category = this.categoryRepository.findOne(id);
        Set<Article> articles = category.getArticles();

        model.addAttribute("articles",articles);
        model.addAttribute("category",category);
        model.addAttribute("view","home/list-articles");

        return "base-layout";
    }

    @GetMapping("/category/{id}/page/{page}/size/{size}")
    public String listArticles(
            Model model,
            @PathVariable("id") Integer id,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size){

        model.addAttribute("view","home/list-articles");


        if(!this.categoryRepository.exists(id)){
            return "redirect:/";
        }

        Integer currentPage = page - 1;
        Category category = categoryRepository.getOne(id);
        Integer articlesNumberByCategory = this.articleRepository.countAllByCategory(category);
        Integer allPages;
        Integer pageIndex = this.userService.getUserPrincipal().getPageIndex();

        if (articlesNumberByCategory % size != 0){
            allPages = (articlesNumberByCategory / size) + 1;
        }
        else {
            allPages = articlesNumberByCategory / size;
        }

        PageRequest pageRequest = new PageRequest(currentPage, size, Sort.Direction.DESC, "id");
        List<Article> articles = this.articleRepository.findAllByCategory(category, pageRequest);

        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("currentPage", page);
        model.addAttribute("allPages", allPages);
        model.addAttribute("articles",articles);
        model.addAttribute("category",category);

        return "base-layout";

    }
    @PostMapping("/category/{id}/page/{page}/size/{size}")
    public String listArticlesProcess(
            @PathVariable("id") Integer id,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size) {

        User user = this.userService.getUserPrincipal();
        user.setPageIndex(user.getPageIndex()+1);
        this.userRepository.saveAndFlush(user);
        Integer pageIndex = user.getPageIndex();


        return "redirect:/category/" + id + "/page/" + ((5 * pageIndex) - 4) + "/size/" + size;
    }

    @PostMapping("/category/{id}/page/{page}/size/{size}/prev")
    public String listArticlesProcess2(@PathVariable("id") Integer id,
                                       @PathVariable("page") Integer page,
                                       @PathVariable("size") Integer size){

        User user = this.userService.getUserPrincipal();
        user.setPageIndex(user.getPageIndex()-1);


        this.userRepository.saveAndFlush(user);
        Integer pageIndex = user.getPageIndex();

        return "redirect:/category/" + id + "/page/" + ((5 * pageIndex) - 4) + "/size/" + size;

    }

    @PostMapping("/{id}")
    private String setPageIndexesTo1(@PathVariable Integer id){
        User user = this.articleService.getUserEntity();
        if (user == null) {
            return "redirect:/login";
        }

        user.setPageIndex(1);
        this.userRepository.saveAndFlush(user);
        return "redirect:/category/" + id + "/page/" + 1 + "/size/" + 2;
    }

    @GetMapping("/subView")
    public ModelAndView getSubView(Model model, Integer id){
        model.addAttribute("article",this.articleRepository.getOne(id));
        return new ModelAndView("subView");

    }

}
