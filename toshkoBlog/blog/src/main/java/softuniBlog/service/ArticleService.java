package softuniBlog.service;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.entity.Tag;
import softuniBlog.entity.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;


public interface ArticleService {
    List<Category> getCategoryList();

    void createProcess(ArticleBindingModel articleBindingModel) throws IOException;

    User getUserEntity ();

    Article getArticleById (@PathVariable Integer id);

    String getEncodedStringFromArticle (Article article);

    String getTagStringFromArticle (Article article);

    void editArticle(Article article, ArticleBindingModel articleBindingModel);

    void deleteArticle(Article article);

    boolean isUserAuthorOrAdmin(Article article);

    HashSet<Tag> findTagsFromStrings(String tagString);



}
