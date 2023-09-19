package Hypercell.BlogApp.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="post_title")
   private String title;

    @Column(name="post_desc")
   private String content;

    @Column(name="image_url")
    private String image;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(name="post_date")
   private String createAt;  // this string will be converted to date in the database

    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name="shared_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Post shared_post;


    @Transient
    Integer sharedPostId;

//    @Transient
    @Column(name="privacy") // canceled
    private PrivacyEnum privacy;


    // numberOfReacts
//
    @Transient
    Integer numberOfComments;

    @Transient
    Integer numberOfReacts;

    @Transient
    Integer isReact;

    // numberOfComments
    // isReactedByMe / friend 0=>noReact 1=>love 2=>like
    // return the whole user object
}
