package softuniBlog.service;




import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.Role;

import java.util.List;

public interface AdminUserService {

    void editUser (@PathVariable Integer id,  UserEditBindingModel userEditBindingModel);
    void deleteUserById (@PathVariable Integer id);
    List<Role> getAllRoles ();
//    void fixLikeCounter();
}
