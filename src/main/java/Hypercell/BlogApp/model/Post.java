package Hypercell.BlogApp.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

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
   private String post_date;

//    @Column(name="user_id")
//    private int user_id;
}
