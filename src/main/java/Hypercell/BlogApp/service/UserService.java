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

    User addFriend(Integer friendId,Integer userId); //add friend to the user with id=userId

    List<User> getFriends(Integer userId); //get friends of user with id=userId

    boolean deleteFriend(Integer friendId,Integer userId); //delete the friend with friendId from user with userId



}
