package softuniBlog.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    private Integer id;
    private String name;
    private Set<User> users;

    public Role() {
        this.users = new HashSet<>();

    }

    @ManyToMany (mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column (name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getSimpleName (){
        return org.springframework.util.StringUtils.capitalize(this.getName().substring(5).toLowerCase());

    }
}
