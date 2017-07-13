package softuniBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.entity.*;
import softuniBlog.repository.*;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ArticleServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository,
                              UserRepository userRepository, TagRepository tagRepository,
                              LikeRepository likeRepository, CommentRepository commentRepository) {

        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Category> getCategoryList() {
        return this.categoryRepository.findAll();
    }

    @Override
    public void createProcess(ArticleBindingModel articleBindingModel) throws IOException {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());
        Category category = this.categoryRepository.findOne(articleBindingModel.getCategoryId());
        HashSet<Tag> tags = this.findTagsFromStrings(articleBindingModel.getTagString());

        Article articleEntity = new Article(
                articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity,
                category,
                tags,
                0,
                articleBindingModel.getMultipartFile().getBytes()
        );

        this.articleRepository.saveAndFlush(articleEntity);
    }

    @Override
    public User getUserEntity() {
        User entityUser;
        if (!(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {

            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            entityUser = this.userRepository.findByEmail(principal.getUsername());
            return entityUser;
        }
        else {
            return null;
        }
    }

    @Override
    public Article getArticleById(Integer id) {

        if (!this.articleRepository.exists(id)){
            return null;
        }
        else {
           return this.articleRepository.findOne(id);
        }
    }

    @Override
    public String getEncodedStringFromArticle(Article article) {
        return Base64.getEncoder().encodeToString(article.getAuthor().getPicture());
    }


    @Override
    public String getTagStringFromArticle(Article article) {
        return article.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.joining(", "));
    }


    @Override
    public void editArticle(Article article, ArticleBindingModel articleBindingModel) {
        Category category = this.categoryRepository.findOne(articleBindingModel.getCategoryId());
        HashSet<Tag> tags = this.findTagsFromStrings(articleBindingModel.getTagString());


        article.setContent(articleBindingModel.getContent());
        article.setTitle(articleBindingModel.getTitle());
        article.setCategory(category);
        article.setTags(tags);

        this.articleRepository.saveAndFlush(article);
    }


    @Override
    public void deleteArticle(Article article) {

        List<Like> likes = article.getLikes();
        this.likeRepository.delete(likes);
        List<Comment> comments = article.getComments();
        this.commentRepository.delete(comments);
        this.articleRepository.delete(article);

    }



    @Override
    public boolean isUserAuthorOrAdmin(Article article) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());
        return userEntity.isAdmin() || userEntity.isAuthor(article);
    }

    @Override
    public HashSet<Tag> findTagsFromStrings(String tagString) {
        HashSet<Tag> tags = new HashSet<>();

        String[] tagNames = tagString.split(",\\s*");

        for (String tagName : tagNames) {
            Tag currentTag = this.tagRepository.findByName(tagName);

            if (currentTag == null) {
                currentTag = new Tag(tagName);
                this.tagRepository.saveAndFlush(currentTag);
            }

            tags.add(currentTag);
        }
        return tags;
    }


}
