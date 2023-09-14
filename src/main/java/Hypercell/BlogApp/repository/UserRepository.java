package Hypercell.BlogApp.repository;

import Hypercell.BlogApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

        User findByEmail(String email);
//        <T>Optional<T> save(Class<T>tClass, User user);



       // User saveFriend(User friend);
        //void deleteFriend(User friend);
}
