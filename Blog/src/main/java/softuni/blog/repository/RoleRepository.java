package softuni.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.blog.entity.Role;

/**
 * Created by yovo on 10-07-2017.
 */
public interface RoleRepository  extends JpaRepository <Role,Integer>{
    Role findByName(String name);
}
