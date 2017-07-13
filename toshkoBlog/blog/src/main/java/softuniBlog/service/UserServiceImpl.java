package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Role;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.RoleRepository;
import softuniBlog.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public void registerUser(UserBindingModel userBindingModel) {
        if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            return;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = null;
        try {
            user = new User(userBindingModel.getEmail(), userBindingModel.getFullName(),
                    bCryptPasswordEncoder.encode(userBindingModel.getPassword()), userBindingModel.getHobby(),
                    userBindingModel.getMultipartFile().getBytes(), userBindingModel.getGender());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.addRole(userRole);


        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
    }

    @Override
    public User getUserPrincipal() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userRepository.findByEmail(principal.getUsername());
        return user;
    }

    @Override
    public List<User> getAllUsers() {


        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        if (!this.userRepository.exists(id)){
            return null;
        }
        return this.userRepository.findOne(id);
    }

    @Override
    public List<Article> getAllArticlesByAuthorId(Integer id) {
        return this.articleRepository.findAllByAuthor_Id(id);
    }
}
