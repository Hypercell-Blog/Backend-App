package Hypercell.BlogApp.model;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-mm-dd")
    private String reaction_date;


    @JsonIgnore
    @Id
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @Id
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn (name="post_id")
    private Post post;


   @Transient
    private int postId;
   @Transient
    private int userId;


    @Data

    @Embeddable
    public static class CompositeKey implements Serializable {
        private User user;
        private Post post;
        public CompositeKey(){}

        public CompositeKey(User user, Post post) {
            this.user = user;
            this.post = post;
        }
    }

    // return the whole user object
    // 1=>like 2=>love

}


/*
* export interface APIResponse {
  success: boolean,
  data:any,
  messages:string[],
  pageNumber?:number,
  totalPages?:number,
  itemsPerPage?:number
}
*
*
* */
