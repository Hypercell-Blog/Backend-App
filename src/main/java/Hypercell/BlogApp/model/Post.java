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
   private String post_title;

    @Column(name="post_desc")
   private String post_desc;

    @Column(name="image_url")
    private String image_url;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(name="post_date")
   private String post_date;  // this string will be converted to date in the database

    @JoinColumn(name="user_id")
    @ManyToOne
    @JsonIgnore
    private User user;

    @JoinColumn(name="shared_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Post shared_post;

    @Transient
    private String user_name;

    @Transient
    Integer shared_post_id;

//    @Transient
    @Column(name="privacy")
    private PrivacyEnum privacy;
}
