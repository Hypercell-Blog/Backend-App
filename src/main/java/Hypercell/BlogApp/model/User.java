package Hypercell.BlogApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "birthdate")
    String  birthdate;

}

