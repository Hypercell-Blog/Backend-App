package Hypercell.BlogApp.service;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.model.response.body.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> addUser(User user);
    Optional<User> getUser(int id);
    Optional<User> updateUser(User user, int id);
    boolean deleteUser(int id);
    List<User> getUsers();

    Response validateUser(String email, String password) throws GeneralException;

}
