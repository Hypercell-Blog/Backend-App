package Hypercell.BlogApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.jdi.PrimitiveValue;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;


@Data
@ToString
@Entity
@Table(name="comment")
public class Comment {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(name="content")
    private String content;

    @Column(name="comment_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String commentDate;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Transient
    private int postId;

    @Transient
    private int userId;



}
