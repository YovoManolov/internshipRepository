package softuniBlog.service;


import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by todor on 3.8.2017 г..
 */

public interface UserService {

    void registerUser (UserBindingModel userBindingModel);
    void logout (HttpServletRequest request, HttpServletResponse response);
    User getUserPrincipal ();
    List<User> getAllUsers ();
    User getUserById (@PathVariable Integer id);
    List<Article> getAllArticlesByAuthorId(Integer id);

}
