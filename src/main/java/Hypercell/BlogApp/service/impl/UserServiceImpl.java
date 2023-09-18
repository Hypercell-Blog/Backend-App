package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.model.response.body.LoginResponse;
import Hypercell.BlogApp.model.response.body.Response;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> addUser(User user) throws GeneralException {

        if(userRepository.findByEmail(user.getEmail()) != null)
            throw new GeneralException("1", "Email Already Exists");
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> getUser(int id) throws GeneralException {
        if(!userRepository.existsById(id))
        {
            throw new GeneralException("1", "User Not Found");
        }
       return Optional.of(userRepository.findById(id).orElseThrow());
    }

    @Override
    public Optional<User> updateUser(User user, int id) {
if(!userRepository.existsById(id))
            return Optional.empty();
    User crnt = userRepository.findById(id).orElseThrow();
        user.setId(id);
        user.setFriends(crnt.getFriends());

        return Optional.of(userRepository.saveAndFlush(user));
    }

    @Override
    public boolean deleteUser(int id) {
        if(!userRepository.existsById(id))
            return false;

        userRepository.deleteById(id);
        return  true;

    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public LoginResponse validateUser(String email , String password) throws GeneralException {
        User user = userRepository.findByEmail(email);
        if(user == null ){
            throw new GeneralException("1", "User Not Found");
        }
        if(!user.getPassword().equals(password)){
            throw new GeneralException("2", "Invalid Credentials");
        }

        return new LoginResponse(user.getId());
    }

    @Override
    public User addFriend(Integer friendId, Integer userId) {
        User user =userRepository.findById(userId).orElseThrow(null); //get user with id=userId
        User friend = userRepository.findById(friendId).orElse(null);

        if (user != null && friend != null) {
            user.getFriends().add(friend); //add to his list of friends 'Friend'
            userRepository.save(user);
            friend.getFriends().add(user);
            userRepository.save(friend);
        }
        return friend;
    }


    @Override
    public List<User> getFriends(Integer userId) {
        User user=userRepository.findById(userId).orElseThrow();
        List<User> friends=user.getFriends();
        return friends;
    }

    @Override
    public boolean deleteFriend(Integer friendId, Integer userId) {
       User user=userRepository.findById(userId).orElseThrow(); //get the user with this userId
       User friend=userRepository.findById(friendId).orElseThrow(); //get the friend with this friendId
        if (user != null && friend != null){
            user.getFriends().remove(friend); //remove this friend from the user's list of friends
            friend.getFriends().remove(user);
            userRepository.saveAndFlush(friend);
        }

        return true;
    }

    @Override
    public boolean isFriend(Integer friendId, Integer userId) throws GeneralException {

        User user=userRepository.findById(userId).orElse(null); //get the user with this userId
        User friend=userRepository.findById(friendId).orElse(null); //get the friend with this friendId
       if( user == null || friend == null)
           throw new GeneralException("1", "User Not Found");

       return user.getFriends().contains(friend);
    }


}
