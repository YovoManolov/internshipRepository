package softuniBlog.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.entity.Role;
import softuniBlog.entity.User;
import softuniBlog.service.AdminUserService;
import softuniBlog.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(UserService userService, AdminUserService adminUserService) {
        this.userService = userService;
        this.adminUserService = adminUserService;
    }


    @GetMapping("/")
    public String listUsers (Model model){
       List <User> users = this.userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("view","admin/user/list");

        return "base-layout";
    }

    @GetMapping("/edit/{id}")
    public String edit (@PathVariable Integer id, Model model){


        User user = this.userService.getUserById(id);
        if (user == null){
            return "redirect:/admin/users/";
        }

        List<Role> roles = this.adminUserService.getAllRoles();

        model.addAttribute("user",user);
        model.addAttribute("roles",roles);
        model.addAttribute("view", "admin/user/edit");

        return "base-layout";


    }

    @PostMapping("/edit/{id}")
    public String editProcess (@PathVariable Integer id, UserEditBindingModel userEditBindingModel){


        this.adminUserService.editUser(id, userEditBindingModel);

        return "redirect:/admin/users/";
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Integer id, Model model){


        User user = this.userService.getUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("view", "admin/user/delete");

        return "base-layout";

    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id){

        this.adminUserService.deleteUserById(id);
//        this.adminUserService.fixLikeCounter();

        return "redirect:/admin/users/";
    }
}
