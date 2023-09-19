package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.model.response.body.LoginResponse;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.UserService;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> addUser(User user) throws GeneralException {

        if(userRepository.findByEmail(user.getEmail()) != null)
            throw new GeneralException("1", "Email Already Exists");
        String hashedPassword = hashing(user.getPassword());
        user.setPassword(hashedPassword);

        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> getUser(int id) throws GeneralException {
        if(!userRepository.existsById(id))
        {
            throw new GeneralException("1", "User Not Found");
        }
        User user = userRepository.findById(id).orElseThrow();

         user.setPic(getImage(user));
       return Optional.of(user);
    }

    String getImage(User user){
        Path path = Paths.get(user.getPic());
        File file=new File(path.toAbsolutePath().toString());
        String base64Image="";
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                base64Image += myReader.nextLine();

            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return base64Image;
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
    public LoginResponse validateUser(String email , String password) throws GeneralException{
        User user = userRepository.findByEmail(email);
        password = hashing(password);

        if(user == null ){
            throw new GeneralException("1", "User Not Found");
        }

        if(!user.getPassword().equals(password)){
            throw new GeneralException("2", "Invalid Credentials");
        }


        return new LoginResponse(user.getId());
    }

    @Override
    public User addFriend(Integer friendId, Integer userId) throws GeneralException {
        User user =userRepository.findById(userId).orElseThrow(null); //get user with id=userId
        

        if(user == null)
            throw new GeneralException("1", "User Not Found");
        if(user.getId() == friendId)
            throw new GeneralException("2", "You can't add yourself as a friend");


        User friend = userRepository.findById(friendId).orElse(null);

        if (friend == null)
            throw new GeneralException("3", "Friend Not Found");


        user.getFriends().add(friend); //add to his list of friends 'Friend'
        userRepository.save(user);
        friend.getFriends().add(user);
        userRepository.save(friend);

        return friend;
    }


    @Override
    public List<User> getFriends(Integer userId) {
        User user=userRepository.findById(userId).orElseThrow();
        return user.getFriends();
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

    @Override
    public String uploadPicture(String image,int userId) throws IOException {
        Path path = Paths.get(String.format("Hypercell/Uploads/User/%s", userId));
        Files.createDirectories(path);
        path = Paths.get(String.format("%s/%s.txt", path, userId));
        if (Files.notExists(path))
            Files.createFile(path);
        try (FileOutputStream fos = new FileOutputStream(path.toAbsolutePath().toString())) {
            fos.write(image.getBytes());
        } catch (Exception ex) {
            System.out.println("Image cannot be uploaded");
        }
        String imagePath=path.toAbsolutePath().toString();
        User user=userRepository.findById(userId).orElseThrow();
        user.setPic(imagePath);
        userRepository.saveAndFlush(user);
        System.out.println(user.getPic());
        return imagePath;
    }

    @Override
    public boolean deletePicture(int userId) throws IOException {
        User user=userRepository.findById(userId).orElseThrow();
        Path path = Paths.get(String.format("Hypercell/Uploads/User/%s", userId));
        Files.createDirectories(path);
        path = Paths.get(String.format("%s/%s.txt", path, userId));
        if(Files.deleteIfExists(path)){
            user.setPic(null);
            userRepository.saveAndFlush(user);
            return true;
        }
        System.out.println("File doesn't exist");
        return false;

    }

    @Override
    public String updatePicture(String image, int userId) throws IOException {
        /*User user = userRepository.findById(userId).orElseThrow();
        Path path = Paths.get(String.format("Hypercell/Uploads/User/%s", userId));
        Files.createDirectories(path);
        path = Paths.get(String.format("%s/%s.txt", path, userId));
        if (Files.notExists(path))
            Files.createFile(path);
        try (FileOutputStream fos = new FileOutputStream(path.toAbsolutePath().toString())) {
            fos.write(image.getBytes());
        } catch (Exception ex) {
            System.out.println("Image cannot be uploaded");
        }
        String imagePath=path.toAbsolutePath().toString();*/
        return null;
    }


    String  hashing(String password){
        // Getting MIME encoder
        Base64.Encoder encoder = Base64.getMimeEncoder();

        return encoder.encodeToString(password.getBytes());


    }








}
