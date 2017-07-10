package softuni.blog.config;

/**
 * Created by yovo on 10-07-2017.
 */


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import softuni.blog.entity.User;
import java.util.ArrayList;
import java.util.Collection;

public class BlogUserDetails extends User implements UserDetails {

    //those private fields will keep our current user and his roles
    private ArrayList<String> roles;
    private User user;

    public BlogUserDetails(ArrayList<String> roles, User user) {
        super(user.getEmail(), user.getFullName(),user.getPassword());
        this.roles = roles;
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRoles = StringUtils.collectionToCommaDelimitedString(this.roles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userRoles);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public User getUser(){
        return this.user;
    }
}
