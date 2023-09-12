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
@IdClass(Reactions.CompositeKey.class)
@Data
public class Reactions {

    @Transient
    @JsonIgnore
    List<String> emos = new ArrayList<String>();
    Reactions(){
        emos.add("love");
        emos.add("like");
    }
    @Column(name="emoji")
    private String emoji;
    @Column(name="reaction_date")
    private String reaction_date;

    @Id
    @Column(name="user_id")
    private int user_id;
    @Id
    @Column(name="post_id")
    private int post_id;


    @Data
    @AllArgsConstructor
    public static class CompositeKey implements Serializable {
        private int user_id;
        private int post_id;
    }


}
