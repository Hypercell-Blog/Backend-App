package Hypercell.BlogApp.service;

import Hypercell.BlogApp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> addUser(User user);
    Optional<User> getUser(int id);
    Optional<User> updateUser(User user, int id);
    boolean deleteUser(int id);

    List<User> getUsers();
}
