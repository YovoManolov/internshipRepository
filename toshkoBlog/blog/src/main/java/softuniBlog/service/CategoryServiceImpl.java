package softuniBlog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuniBlog.bindingModel.CategoryBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public List<Category> getCategoryStream(List<Category> categories) {
        return categories.stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());
    }

    @Override
    public void createAndSaveCategory(CategoryBindingModel categoryBindingModel) {

        Category category = new Category(categoryBindingModel.getName());
        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category getCategoryById(Integer id) {

        if (!this.categoryRepository.exists(id)){
            return null;
        }

        return this.categoryRepository.findOne(id);
    }

    @Override
    public void setAndSaveCategory(Category category, CategoryBindingModel categoryBindingModel) {
        category.setName(categoryBindingModel.getName());

        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public void deleteCategory(Category category) {
        for (Article article : category.getArticles()){
            this.articleRepository.delete(article);
        }

        this.categoryRepository.delete(category);
    }

    @Override
    public void saveCategory(Category category) {
        this.categoryRepository.saveAndFlush(category);
    }
}
