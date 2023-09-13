package Hypercell.BlogApp.model;

import Hypercell.BlogApp.model.Serializer.CustomSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
//    @JsonSerialize(using = CustomSerializer.class)
    List<String> emos = new ArrayList<String>();
    Reactions(){
        emos.add("love");
        emos.add("like");
    }
    @Column(name="emoji")
    private String emoji;
    @Column(name="reaction_date")
    private String reaction_date;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn (name="post_id")
    private Post post;


    @Id
    @Column(name="post_id")
    private int post_id;
    @Id
    @Column(name="user_id")
    private int user_id;



    @Data
    @AllArgsConstructor
    @Embeddable
    public static class CompositeKey implements Serializable {
        private int user_id;
        private int post_id;
        public CompositeKey(){}
    }



}
