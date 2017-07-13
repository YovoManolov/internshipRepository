package softuniBlog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Category;
import softuniBlog.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/")
    public String list (Model model){
        List<Category> categories = this.categoryService.getAllCategories();
        categories = this.categoryService.getCategoryStream(categories);

        model.addAttribute("categories",categories);
        model.addAttribute("view","admin/category/list");

        return "base-layout";
    }


    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("view", "admin/category/create");

        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess (CategoryBindingModel categoryBindingModel){

        if (StringUtils.isEmpty(categoryBindingModel.getName())){

            return "redirect:/admin/categories/create";
        }

        this.categoryService.createAndSaveCategory(categoryBindingModel);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String edit (Model model, @PathVariable Integer id){

        Category category = this.categoryService.getCategoryById(id);

        if(category == null){
            return "redirect:/admin/categories/";
        }

        model.addAttribute("category",category);
        model.addAttribute("view","admin/category/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel){

        Category category = this.categoryService.getCategoryById(id);

        if(category == null){
            return "redirect:/admin/categories/";
        }

        this.categoryService.setAndSaveCategory(category,categoryBindingModel);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/delete/{id}")
    public String delete (Model model, @PathVariable Integer id){
        Category category = this.categoryService.getCategoryById(id);

        if(category == null){
            return "redirect:/admin/categories/";
        }

        model.addAttribute("category",category);
        model.addAttribute("view","admin/category/delete");

        return "base-layout";

    }

    @PostMapping("/delete/{id}")
    public String deleteProcess (@PathVariable Integer id){
        Category category = this.categoryService.getCategoryById(id);

        if(category == null){
            return "redirect:/admin/categories/";
        }

       this.categoryService.deleteCategory(category);

        return "redirect:/admin/categories/";
    }
}
