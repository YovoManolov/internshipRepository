package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import softuniBlog.entity.Article;
import softuniBlog.entity.Like;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.LikeRepository;
import softuniBlog.repository.UserRepository;

import java.util.List;


@Service
public class LikeServiceImpl implements LikeService {

    private final ArticleRepository articleRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(ArticleRepository articleRepository, LikeRepository likeRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void likeArticleById(Integer id) {

        if (!this.articleRepository.exists(id)){
            return;
        }

        Article article = this.articleRepository.findOne(id);
        Integer likeCounter = articleRepository.getOne(id).getLikeCounter();
        List<Like> likes = this.likeRepository.findAll();

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = this.userRepository.findByEmail(user.getUsername());

        Integer userId = userEntity.getId();
        Boolean flag = false;


        for (Like l: likes) {
            if (l.getUser().getId() == userId && l.getArticle().getId() == id){
                flag = true;
                if (l.getLiked()){
                    l.setLiked(false);
                    likeCounter--;
                }
                else {
                    l.setLiked(true);
                    likeCounter++;
                }
                break;
            }
        }
        if (!flag){
            likes.add(new Like(article, userEntity, true));
            likeCounter++;
        }
        article.setLikeCounter(likeCounter);
        this.articleRepository.saveAndFlush(article);
        this.likeRepository.save(likes);

    }
}
