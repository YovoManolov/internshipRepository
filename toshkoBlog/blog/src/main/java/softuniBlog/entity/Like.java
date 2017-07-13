package softuniBlog.entity;



import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Like {


    private Integer id;
    private Article article;
    private User user;
    private Boolean isLiked;

    public Like(Article article, User user, Boolean isLiked) {
        this.article = article;
        this.user = user;
        this.isLiked = isLiked;
    }

    public Like() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "articleId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @ManyToOne()
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user= user;
    }

    @Column(name = "isLiked")
    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

}
