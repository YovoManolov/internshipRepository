package softuniBlog.service;

import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by todor on 2.8.2017 г..
 */
public interface LikeService {

    void likeArticleById(@PathVariable Integer id);

}
