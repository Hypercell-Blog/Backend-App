package Hypercell.BlogApp.controller;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Credentials;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.model.response.body.Response;
import Hypercell.BlogApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("signup")
    public ResponseEntity<?> addUser(@RequestBody User user)
    {
        User res = userService.addUser(user).orElse(null);
        if(res != null)
            return ResponseEntity.ok(res);
        String msg = "User already exists";
        return new ResponseEntity<>("User already exists", HttpStatus.OK);

    }

    @PostMapping("login")
    public ResponseEntity<?> validateUser(@RequestBody Credentials userCredential) throws GeneralException {

        Response response = userService.validateUser(userCredential.getEmail(), userCredential.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);

//        if(user == null)
//            return new ResponseEntity<>("Invalid Credentials", HttpStatus.OK);
//
//        return ResponseEntity.ok(user);
    }

    @PutMapping("update/{id}")
    public User updateUser(@RequestBody UserController user, @PathVariable("id") int id) {
        return user.updateUser(user, id);
    }

    @DeleteMapping("delete/{id}")
    public boolean deleteUser(@PathVariable("id") int id) {
        return userService.deleteUser(id);
    }

    @GetMapping("get/{id}")
    public User getUser(@PathVariable ("id") int id){
        return  userService.getUser(id).orElse(null);

    }

    @GetMapping("get/all")
    public List<User>  getUsers(){
        return userService.getUsers();
    }


    @PostMapping("/add/friend")
    public User addFriend(@RequestParam("user-id") Integer userId,@RequestParam("friend-id") Integer friendId){
        return userService.addFriend(friendId,userId);
    }

    @GetMapping("get/friends/{user-id}")
    public List<User> getFriends(@PathVariable("user-id") Integer userId){
        List<User> friends=userService.getFriends(userId);
        return friends;
    }

    @DeleteMapping("/delete/friend")
    public boolean deleteFriend(@RequestParam("user-id") Integer userId,@RequestParam("friend-id") Integer friendId){
        return userService.deleteFriend(friendId,userId);
    }

}
