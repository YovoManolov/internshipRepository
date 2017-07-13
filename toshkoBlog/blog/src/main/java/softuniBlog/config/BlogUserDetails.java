package softuniBlog.config;

import org.springframework.util.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import softuniBlog.entity.User;
import java.util.ArrayList;
import java.util.Collection;



public class BlogUserDetails extends User implements UserDetails {

    private ArrayList<String> roles;
    private User user;



    public BlogUserDetails(User user, ArrayList<String> roles) {
        super(user.getEmail(), user.getFullName(), user.getPassword(),user.getHobby(), user.getPicture(), user.getGender());
        this.user = user;
        this.roles = roles;
    }

    public User getUser (){
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        String userRoles = StringUtils.collectionToCommaDelimitedString(this.roles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles);
    }

    @Override
    public String getUsername() {
        return this.user.getFullName();
    }

    @Override
    public boolean isAccountNonExpired (){
        return true;
    }

    @Override
    public boolean isAccountNonLocked (){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired (){
        return true;
    }

    @Override
    public boolean isEnabled (){
        return true;
    }
}
