package Hypercell.BlogApp.service.impl;
import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.*;
import Hypercell.BlogApp.model.response.body.Response;
import Hypercell.BlogApp.repository.CommentRepository;
import Hypercell.BlogApp.repository.PostRepository;
import Hypercell.BlogApp.repository.ReactionsRepository;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.UserService;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class postInterfaceImpl implements postInterface {

    private final PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReactionsRepository reactionsRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;




    public postInterfaceImpl(PostRepository postrepository){
        this.postRepository=postrepository;
    }


    @Override
    public Post addPost(Post post, Integer id) {

        User user = userRepository.findById(id).orElseThrow();
        System.out.println(post.getImage());
        post.setImage("");

        // here if the post is shared post we will get the original post and set it to the shared post
        if(post.getSharedPostId() != null) {
            Post shared_post = postRepository.findById(post.getSharedPostId()).orElseThrow();
            if (shared_post.getSharedPost() != null) {
                shared_post = shared_post.getSharedPost();
                System.out.println("shared post is not null");
            }
            post.setSharedPost(shared_post);
        }
        if(post.getCreateAt() == null){
            post.setCreateAt(java.time.LocalDate.now().toString());
        }

        post.setUser(user);
        return postRepository.save(post);
    }


    @Override
    public Post updatePost(Post post, int id) throws GeneralException {

        if(id <0 || !postRepository.existsById(id)){
            throw new GeneralException("1","Post is not found");
        } else{
            Post crntPost = postRepository.findById(id).orElseThrow();
            post.setId(id);
            post.setUser(crntPost.getUser());

            return postRepository.saveAndFlush(post);
        }
    }

    @Override
    public boolean deletePost(int id) throws GeneralException {

        if(id <0 || !postRepository.existsById(id)){
            throw new GeneralException("1", "Post Not Found");
        } else {

            List<Post> posts = postRepository.findAllByShared_post(id);
            postRepository.deleteAll(posts);

            postRepository.deleteById(id);
            return true ;

        }
    }

    String getImage(Post post){
        System.out.println("here the path");
        System.out.println(post.getImage());
        Path path = Paths.get(post.getImage());
        File file = new File(path.toAbsolutePath().toString());
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

    String getImage(User user){
        if(user.getPic()== null){
            return null;
        }
        Path path = Paths.get(user.getPic());
        File file = new File(path.toAbsolutePath().toString());
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
    public Post getPost(int id) {
        Post post = postRepository.findById(id).orElseThrow();
        if(post.getImage() != null){
            post.setImage(getImage(post));
        }
        if(post.getUser().getPic() != null){

            post.getUser().setPic(getImage(post.getUser()));
        }
        return post;
    }

    @Override
    public List<Post> getPosts(Integer userId,Integer friendId ) throws GeneralException {

        if(userRepository.findById(userId).isEmpty()){
            throw new GeneralException("1","User is not found");
        }
        Map<Integer, String> userImages  = new HashMap<>();
       List<Post> posts= postRepository.findByUserId(friendId);
        for (Post post : posts) {
            if(post.getImage() != null){

                post.setImage(getImage(post));
            }
            if(post.getUser().getPic() != null){
                if(userImages.get(post.getUser().getId()) == null){
                    userImages.put(post.getUser().getId(),getImage(post.getUser()))  ;
                }
                post.getUser().setPic(userImages.get(post.getUser().getId()));
            }


            List<Reactions> rec = reactionsRepository.findAllByPost(post);
            Optional<User> user = userRepository.findById(userId);
            User crntUser = user.get();

            if(reactionsRepository.findById(new Reactions.CompositeKey(crntUser, post)).isPresent()){
                Optional<Reactions> reaction = reactionsRepository.findById(new Reactions.CompositeKey(crntUser, post));
                post.setIsReact(reaction.get().getType().ordinal());
            }

            post.setNumberOfReacts(rec.size());
            List<Comment> comments = commentRepository.findByPost(post);
            post.setNumberOfComments(comments.size());


        }
        return posts;
    }


    @Override
    public List<Post> getAllPosts(int id) throws GeneralException {
        if(userRepository.findById(id).isEmpty()){
            throw new GeneralException("1","User is not found");
        }
        Map<Integer, String> userImages  = new HashMap<>();


        List<Post> posts = postRepository.findAll();
        for(Post post: posts){

            if(post.getImage() != null){
             post.setImage(getImage(post));
            }

            if(post.getUser().getPic() != null){
                if(userImages.get(post.getUser().getId())!= null){
                    userImages.put(post.getUser().getId(), getImage(post.getUser()));
//                    post.getUser().setPic();
                }

                post.getUser().setPic(userImages.get(post.getUser().getId()));
            }


//            if(post.getSharedPost() != null){
//                if(post.getSharedPost().getImage() != null){
//                    post.getSharedPost().setImage(getImage(post.getSharedPost()));
//                }
//            }

            List<Reactions> rec = reactionsRepository.findAllByPost(post);
            Optional<User> user = userRepository.findById(id);
            User crntUser = user.get();

            if(reactionsRepository.findById(new Reactions.CompositeKey(crntUser, post)).isPresent()){
                Optional<Reactions> reaction = reactionsRepository.findById(new Reactions.CompositeKey(crntUser, post));

                post.setIsReact(reaction.get().getType().ordinal());
            }

            post.setNumberOfReacts(rec.size());
            List<Comment> comments = commentRepository.findByPost(post);
            post.setNumberOfComments(comments.size());



        }
        return posts;





    }


    public String uploadPicture(String image, int postId) throws IOException {
        Path path = Paths.get(String.format("Hypercell/Uploads/Post/%s", postId));
        Files.createDirectories(path);
        path = Paths.get(String.format("%s/%s.txt", path, postId));
        if (Files.notExists(path))
            Files.createFile(path);
        try (FileOutputStream fos = new FileOutputStream(path.toAbsolutePath().toString())) {
            fos.write(image.getBytes());
        } catch (Exception ex) {
            System.out.println("Image cannot be uploaded");
        }
        String imagePath=path.toAbsolutePath().toString();
        Post post=postRepository.findById(postId).orElseThrow();
        post.setImage(imagePath);
        System.out.println("here the path");
        System.out.println(imagePath);
        postRepository.saveAndFlush(post);
        System.out.println(post.getImage());
        return imagePath;

    }



    @Override
    public boolean deletePicture(int postId) throws IOException {
        Post post=postRepository.findById(postId).orElseThrow();
        Path path = Paths.get(String.format("Hypercell/Uploads/Post/%s", postId));
        Files.createDirectories(path);
        path = Paths.get(String.format("%s/%s.txt", path, postId));
        if(Files.deleteIfExists(path)){
            post.setImage(null);
            postRepository.saveAndFlush(post);
            return true;
        }
        return false;


    }
}











//    @Override
//    public Post getPost(int id,int userId,int friendId) throws GeneralException {
//        Post post = postRepository.findById(id).orElseThrow();
//        if(id <0 || !postRepository.existsById(id)){
//            throw new GeneralException("1","Id is not found");
//        }
//        else if (post.getPrivacy()==PrivacyEnum.PUBLIC){
////            post.setUser_name(post.getUser().getName());
////            if(post.getShared_post()!= null){
////                post.getShared_post().setUser_name(post.getShared_post().getUser().getName());
////            }
//            return post;
//        } else if(post.getPrivacy()==PrivacyEnum.FRIENDS){         //Friends
//            User user=userRepository.findById(userId).orElse(null); //get the user with this userId
//            User friend=userRepository.findById(friendId).orElse(null); //get the friend with this friendId
//            if( user == null || friend == null)
//                throw new GeneralException("1","User Not Found");
//
//      return post;
//
//        }else {                                          //OnlyME
//           throw new GeneralException("1","Post Is Not Available");
//        }
//    }

//    @Override
//    public List<Post> getPosts(Integer userId,Integer friendId) throws GeneralException {
//        if(userRepository.findById(userId).isEmpty()){
//            throw new GeneralException("1","User is not found");
//        }
//        User user = userRepository.findById(userId).orElseThrow();
//        List<Post> result = postRepository.findByUserId(userId);
//        List<Post> finalPost=new ArrayList<>();
//        for (Post post : result) {
//
//            if (post.getPrivacy() == PrivacyEnum.PUBLIC) {
////                post.setUser_name(user.getName());
////                if (post.getShared_post() != null) {
////                    post.getShared_post().setUser_name(post.getShared_post().getUser().getName());
////                }
//                finalPost.add(post);
//            } else if (post.getPrivacy() == PrivacyEnum.FRIENDS) {//get the user with this userId
//                User friend = userRepository.findById(friendId).orElse(null); //get the friend with this friendId
//                if (friend == null)
//                    throw new GeneralException("1","User Not Found");
//                finalPost.add(post);
//
//            } else if (post.getPrivacy() == PrivacyEnum.ONLYME) {
//                continue;
//            }
//        }
//                return finalPost;
//    }

