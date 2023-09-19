package Hypercell.BlogApp.service;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.model.response.body.LoginResponse;
import Hypercell.BlogApp.model.response.body.Response;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> addUser(User user) throws GeneralException, NoSuchAlgorithmException, InvalidKeySpecException;
    Optional<User> getUser(int id) throws GeneralException;
    Optional<User> updateUser(User user, int id);
    boolean deleteUser(int id);
    List<User> getUsers();

    LoginResponse validateUser(String email, String password) throws GeneralException, NoSuchAlgorithmException, InvalidKeySpecException;

    User addFriend(Integer friendId,Integer userId) throws GeneralException; //add friend to the user with id=userId

    List<User> getFriends(Integer userId); //get friends of user with id=userId

    boolean deleteFriend(Integer friendId,Integer userId); //delete the friend with friendId from user with userId

    boolean isFriend(Integer friendId,Integer userId) throws GeneralException; //check if the user with id=userId is friend with the user with id=friendId

    String uploadPicture(String image,int userId) throws IOException;

    boolean deletePicture(int userId) throws IOException;

    String updatePicture(String image,int userId) throws IOException;


}
