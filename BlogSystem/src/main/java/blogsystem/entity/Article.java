package blogsystem.entity;
import javax.persistence.*;

/**
 * Created by yovo on 12-07-2017.
 */
@Entity
@Table(name = "articles")
public class Article {
    public Article(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
    public Article() {  }

    private Integer id;
    private String title;
    private String content;
    private User author;


    @Column(columnDefinition = "text",nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(nullable = false ,name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    //@transient following method shouldn't be saved in the database
    @Transient
    public String getSummary(){
        return this.getContent().substring(0 ,this.getContent().length()/2 )+ "...";
    }


}
