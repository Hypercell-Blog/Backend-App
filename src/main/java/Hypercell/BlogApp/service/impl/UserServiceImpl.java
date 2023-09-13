package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.model.response.body.Response;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> addUser(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null)
            return Optional.empty();
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> getUser(int id) {
       return Optional.of(userRepository.findById(id).orElseThrow());
    }

    @Override
    public Optional<User> updateUser(User user, int id) {

        user.setId(id);
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
    public Response validateUser(String email , String password) throws GeneralException {

        User user = userRepository.findByEmail(email);
        if(user == null ){
            throw new GeneralException("1", "User Not Found");
//            return new Response("1", "User Not Found");
        }
        if(!user.getPassword().equals(password)){
            return new Response("2", "Invalid Credentials");
        }
        return new Response("0", user);
    }
}
