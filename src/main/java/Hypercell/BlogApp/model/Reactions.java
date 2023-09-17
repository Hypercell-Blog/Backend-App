package Hypercell.BlogApp.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="reactions")
@Data
@IdClass(Reactions.CompositeKey.class)
public class Reactions {
    @Transient
    @JsonIgnore
//    @JsonSerialize(using = CustomSerializer.class)
    List<String> emos = new ArrayList<String>();
    public Reactions(){
        emos.add("love");
        emos.add("like");
    }
    @Column(name="emoji")
    private String emoji;
    @Column(name="reaction_date")
    private String reaction_date;


    @JsonIgnore
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name="post_id")
    private Post post;


   @Transient
    private int post_id;
   @Transient
    private int user_id;


    @Data
    @AllArgsConstructor
    @Embeddable
    public static class CompositeKey implements Serializable {
        private User user;
        private Post post;
        public CompositeKey(){}
    }



}
