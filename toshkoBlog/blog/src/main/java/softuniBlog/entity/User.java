package softuniBlog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "users")
public class User {
    private Integer id;
    private String email;
    private String fullName;
    @JsonBackReference
    private String password;
    private String hobby;
    @JsonBackReference
    private Set<Role> roles;
    @JsonBackReference
    private Set<Article> articles;
    @JsonBackReference
    private List<Comment> comments;
    @JsonBackReference
    private Set<Like> likes;
    private byte[] picture;
    private String gender;
    private Integer pageIndex;


    public User(String email, String fullName, String password, String hobby, byte[] picture, String gender) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.hobby = hobby;
        this.roles = new HashSet<>();
        this.articles = new HashSet<>();
        this.comments = new LinkedList<>();
        this.likes = new HashSet<>();
        this.picture = picture;
        this.gender = gender;
        this.pageIndex = 1;


    }

    public User() {
    }

    @Column(name = "pageIndex")
    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "picture")
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    @Column(name = "hobby")
    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }


    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    public Set<Role> getRoles() {
        return roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    @Column(name = "fullName", nullable = false)
    public String getFullName() {
        return fullName;
    }

    @Column(name = "password", length = 60, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRole(Role role) {
        this.roles.add(role);

    }

    @Transient
    public boolean isAdmin() {
        return this.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

    }

    @Transient
    public boolean isAuthor(Article article) {
        return Objects.equals(this.getId(),
                article.getAuthor().getId());

    }

    @Transient
    public boolean isAuthor(Comment comment) {
        return Objects.equals(this.getId(),
                comment.getUser().getId());

    }

    @Transient
    public String encodeImgToString() {

        return Base64.getEncoder().encodeToString(this.getPicture());

    }

    @Transient
    public boolean hasPicture() {
        if (this.getPicture() == null || this.getPicture().length == 0) {
            return false;
        }
        return true;
    }
}