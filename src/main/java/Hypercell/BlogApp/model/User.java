package Hypercell.BlogApp.model;

import Hypercell.BlogApp.model.Serializer.CustomUserSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id ;

    @Column(name = "name")
    String name ;

    @Column(name = "email")
    String email ;

    @Column(name = "password")
    String password ;

//    @JsonFormat(pattern="yyyy-MM-dd")
//    @Column(name = "birthdate")
//    String  birthdate;

    @Column(name ="facebook")
    String facebook;

    @Column(name = "phone")
    String phone;

    @Column(name = "pic")
    String pic;

    @Column(name = "bio")
    String bio;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "friends",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @JsonSerialize(using = CustomUserSerializer.class)
    private List<User> friends;

}

