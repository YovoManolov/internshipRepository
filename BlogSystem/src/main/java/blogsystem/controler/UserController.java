package blogsystem.controler;

import blogsystem.bindingModel.UserBindingModel;
import blogsystem.entity.Role;
import blogsystem.entity.User;
import blogsystem.repository.RoleRepository;
import blogsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller //tell Spring to initialize those fields
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

//    it is said that the fields /roleRepository/ and /userRepository/
//    should be PRIVATE but at the pictures they are not.


    @GetMapping("/register")
    public String register (Model model){
        model.addAttribute("view","user/register");
        return "base-layout";
    }

    //The PostMapping annotation corresponds to the form method.
    // It means that we will receive data from somewhere.
    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel){
        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            return "redirect:/register";
            //The keyword redirect: will change the current route to any given route
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User(userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword()));

        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.addRole(userRole);
        this.userRepository.saveAndFlush(user);
        return "redirect:/login";

    }


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("view","user/login");
        return "base-layout";
    }
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request , HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().
                getPrincipal();

        User user = this.userRepository.findByEmail(principal.getUsername());

        model.addAttribute("user",user);
        model.addAttribute("view","user/profile");

        return "base-layout";
    }
}

