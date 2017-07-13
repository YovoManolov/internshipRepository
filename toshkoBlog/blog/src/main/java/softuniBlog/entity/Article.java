package softuniBlog.entity;



import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "articles")
public class Article {

    private Integer id;
    private String title;
    private String content;
    private User author;
    private Category category;
    private Set<Tag> tags;
    private List<Comment> comments;
    private Integer likeCounter;
    private List<Like> likes;
    private String date;
    private byte [] picture;



    public Article(String title, String content, User author, Category category, HashSet<Tag> tags,
                   Integer likeCounter, byte[] picture) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.tags = tags;
        this.likeCounter = likeCounter;
        this.comments = new LinkedList<>();
        this.likes = new LinkedList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();
        this.date = dateFormat.format(data);
        this.picture = picture;
    }

    public Article (){}

    @Column(name = "picture")
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Column(name ="date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @OneToMany(mappedBy = "article")
    public List<Like> getLikes()
    {
        //Collections.reverse(likes);
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    public java.util.List<Comment> getComments()
    {
        //Collections.reverse(comments);
        //if (this.comments != null) Collections.sort(comments);
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Column(name = "likeCounter")
    public Integer getLikeCounter() {
        return likeCounter;
    }


    public void setLikeCounter(Integer likeCounter) {
        this.likeCounter = likeCounter;
    }

    @ManyToMany
    @JoinColumn(table = "articles_tags")
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column (nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @ManyToOne()
    @JoinColumn(name = "authorId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Transient
    public String getSummary (){
        return this.getContent().substring(0,this.getContent().length()/2) + "...";

    }

    @Transient
    public String getArticleImgString (){
        return Base64.getEncoder().encodeToString(this.getPicture());
    }

    @Transient
    public Boolean articleHasPicture() {
        if (this.getPicture() == null || this.getPicture().length == 0) {
            return false;
        }
        return true;
    }

    @Transient
    public Integer getLikesCount(){
        int i = 0;
        for (Like like: this.getLikes()) {
            if (like.getLiked()){
                i++;
            }

        }
        return i;
    }


}
