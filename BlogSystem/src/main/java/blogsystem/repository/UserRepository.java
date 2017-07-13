package blogsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import blogsystem.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
