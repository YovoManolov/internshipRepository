package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.blog.entity.User;

/**
 * Created by yovo on 10-07-2017.
 */

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
