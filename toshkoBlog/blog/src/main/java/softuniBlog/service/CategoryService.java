package softuniBlog.service;

import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Category;

import java.util.List;

/**
 * Created by todor on 4.8.2017 г..
 */
public interface CategoryService {
    List<Category> getAllCategories ();
    List<Category> getCategoryStream (List <Category> categories);
    void createAndSaveCategory (CategoryBindingModel categoryBindingModel);
    Category getCategoryById (@PathVariable Integer id);
    void setAndSaveCategory (Category category, CategoryBindingModel categoryBindingModel);
    void deleteCategory (Category category);
    void saveCategory(Category category);
}
