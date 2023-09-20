package Hypercell.BlogApp.controller;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Credentials;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.model.requests.ProfileImage;
import Hypercell.BlogApp.model.response.body.LoginResponse;
import Hypercell.BlogApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.imageio.IIOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("add-user")
    public Optional<User> addUser(@RequestBody User user) throws GeneralException, NoSuchAlgorithmException, InvalidKeySpecException {

        return  userService.addUser(user);

    }

    @PostMapping("login")
    public LoginResponse validateUser(@RequestBody Credentials userCredential) throws GeneralException, NoSuchAlgorithmException, InvalidKeySpecException {

        return userService.validateUser(userCredential.getEmail(), userCredential.getPassword());
    }

    @PutMapping("update-user/{id}")
    public Optional<User> updateUser(@RequestBody User user, @PathVariable("id") int id) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("delete/{id}")
    public boolean deleteUser(@PathVariable("id") int id) {
        return userService.deleteUser(id);
    }

    @GetMapping("get-user/{id}")
    public User getUser(@PathVariable ("id") int id) throws GeneralException {
        return  userService.getUser(id);

    }

    @GetMapping("get-user/all")
    public List<User>  getUsers(){
        return userService.getUsers();
    }


    @PostMapping("/add-friend")
    public User addFriend(@RequestParam("userId") Integer userId, @RequestParam("friendId") Integer friendId) throws GeneralException {
        return userService.addFriend(friendId,userId);
    }

    @GetMapping("get-friends/{userId}")
    public List<User> getFriends(@PathVariable("userId") Integer userId) throws GeneralException {
        return userService.getFriends(userId);
    }

    @DeleteMapping("/delete-friend")
    public boolean deleteFriend(@RequestParam("userId") Integer userId,@RequestParam("friendId") Integer friendId){
        return userService.deleteFriend(friendId,userId);
    }

    @PutMapping("check-friend")
    public boolean checkFriend(@RequestParam("userId") int id,@RequestParam("friendId") int friendId) throws GeneralException {
        return userService.isFriend(id,friendId);
    }

    @PostMapping("/upload-image/{userId}")
    public Object uploadImage(@RequestBody ProfileImage image, @PathVariable("userId") Integer userId){
        String res;
        ProfileImage resBody = new ProfileImage();

        try{
            res=userService.uploadPicture(image.getImage(),userId);
             resBody.setImage(res);
        }
        catch(Exception e){
            new GeneralException("1","Error in uploading image");
        }
        return  resBody;



    }

    @DeleteMapping("delete-image/{userId}")
    public boolean deleteImage(@PathVariable("userId") Integer userId){
        boolean isDeleted=false;
        try{
            isDeleted=userService.deletePicture(userId);
            return isDeleted;
        }
        catch(Exception exception){
            exception.printStackTrace();
            return isDeleted;
        }

    }


}
