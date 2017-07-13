package softuniBlog.bindingModel;

import softuniBlog.entity.Article;
import softuniBlog.entity.User;

import javax.validation.constraints.NotNull;

/**
 * Created by todor on 20.7.2017 г..
 */
public class CommentBindingModel {
    private User user;
    private Article article;

    @NotNull
    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
