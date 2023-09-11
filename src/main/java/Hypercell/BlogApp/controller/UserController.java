package Hypercell.BlogApp.controller;

import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user).orElse(null);
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


}
