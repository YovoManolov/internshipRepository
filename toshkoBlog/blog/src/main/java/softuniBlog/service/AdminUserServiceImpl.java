package softuniBlog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.*;
import softuniBlog.repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminUserServiceImpl implements AdminUserService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AdminUserServiceImpl(RoleRepository roleRepository, UserRepository userRepository,
                                ArticleRepository articleRepository, LikeRepository likeRepository,
                                CommentRepository commentRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void editUser(Integer id,  UserEditBindingModel userEditBindingModel) {
        User user = this.userRepository.findOne(id);

        if (!StringUtils.isEmpty(userEditBindingModel.getPassword())
                && !StringUtils.isEmpty(userEditBindingModel.getConfirmPassword())){

            if (userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())){

                BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

                user.setPassword(bcryptPasswordEncoder.encode(userEditBindingModel.getPassword()));

                user.setFullName(userEditBindingModel.getFullName());
                user.setEmail(userEditBindingModel.getEmail());
                user.setHobby(userEditBindingModel.getHobby());

                Set<Role> roles = new HashSet<>();

                for (Integer roleId : userEditBindingModel.getRoles()){
                    roles.add(this.roleRepository.findOne(roleId));
                }
                user.setRoles(roles);


            }
        }
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUserById(Integer id) {
//        if (!this.userRepository.exists(id)){
//            return;
//        }

        User user = this.userRepository.findOne(id);
        List<Article> articles = this.articleRepository.findAll();


        Set<Like> likes = user.getLikes();
        Article articlee = null;

        if (likes != null) {
            for (Like like: likes) {
                articlee = this.articleRepository.getOne(like.getArticle().getId());
                articlee.setLikeCounter(articlee.getLikeCounter() - 1);
                this.articleRepository.saveAndFlush(articlee);
                this.likeRepository.delete(like);

            }
        }

        List<Comment> comments = user.getComments();
        if (comments != null) {
            this.commentRepository.delete(comments);
        }

        for (Article article: articles) {
            if (article.getAuthor().getId().equals(user.getId())){
                this.articleRepository.delete(article);
            }
        }

        this.userRepository.delete(user);
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

//    @Override
//    public void fixLikeCounter() {
//        for (Article article : this.articleRepository.findAll()) {
//            article.setLikeCounter(this.likeRepository.countAllByArticleId(article.getId()));
//        }
//    }
}
