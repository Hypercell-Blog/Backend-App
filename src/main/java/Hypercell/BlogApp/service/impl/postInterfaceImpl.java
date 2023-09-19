package Hypercell.BlogApp.service.impl;
import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.*;
import Hypercell.BlogApp.model.response.body.Response;
import Hypercell.BlogApp.repository.CommentRepository;
import Hypercell.BlogApp.repository.PostRepository;
import Hypercell.BlogApp.repository.ReactionsRepository;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class postInterfaceImpl implements postInterface {

    private final PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReactionsRepository reactionsRepository;

    @Autowired
    private CommentRepository commentRepository;




    public postInterfaceImpl(PostRepository postrepository){
        this.postRepository=postrepository;
    }


    @Override
    public Post addPost(Post post, Integer id) {
        User user = userRepository.findById(id).orElseThrow();

        // here if the post is shared post we will get the original post and set it to the shared post
        if(post.getSharedPostId() != null) {
            Post shared_post = postRepository.findById(post.getSharedPostId()).orElseThrow();
            if (shared_post.getShared_post() != null) {
                shared_post = shared_post.getShared_post();
                System.out.println("shared post is not null");
            }
            post.setShared_post(shared_post);
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
//hello
    @Override
    public Post getPost(int id,int userId,int friendId) throws GeneralException {
        Post post = postRepository.findById(id).orElseThrow();
        if(id <0 || !postRepository.existsById(id)){
            throw new GeneralException("1","Id is not found");
        }
        else if (post.getPrivacy()==PrivacyEnum.PUBLIC){
//            post.setUser_name(post.getUser().getName());
//            if(post.getShared_post()!= null){
//                post.getShared_post().setUser_name(post.getShared_post().getUser().getName());
//            }
            return post;
        } else if(post.getPrivacy()==PrivacyEnum.FRIENDS){         //Friends
            User user=userRepository.findById(userId).orElse(null); //get the user with this userId
            User friend=userRepository.findById(friendId).orElse(null); //get the friend with this friendId
            if( user == null || friend == null)
                throw new GeneralException("1","User Not Found");

            boolean friends =user.getFriends().contains(friend);
            if(friends) {

//                post.setUser_name(post.getUser().getName());
//                if(post.getShared_post()!= null){
//                    post.getShared_post().setUser_name(post.getShared_post().getUser().getName());
//                }
                return post;

            }else{
                throw new GeneralException("1","Post Is Not Available");
            }

        }else {                                          //OnlyME
           throw new GeneralException("1","Post Is Not Available");
        }
    }

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

    @Override
    public Post getPost(int id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Post> getPosts(Integer userId,Integer friendId ) throws GeneralException {

        if(userRepository.findById(userId).isEmpty()){
            throw new GeneralException("1","User is not found");
        }
       List<Post> posts= postRepository.findByUserId(friendId);
        for (Post post : posts) {

            if(post.getUser() == null){
                continue;
            }
            List<Reactions> rec = reactionsRepository.findAllByPost(post);
            Optional<User> user = userRepository.findById(userId);
            User crntUser = user.get();

            if(reactionsRepository.findById(new Reactions.CompositeKey(crntUser, post)).isPresent()){
                post.setIsReact(1);
            }

            post.setNumberOfReacts(rec.size());
            List<Comment> comments = commentRepository.findByPost(post);
            post.setNumberOfComments(comments.size());


        }
        return posts;
    }

    @Override
    public List<Post> getAllPosts(int id) throws GeneralException {

        List<Post> posts = postRepository.findAll();
        for(Post post: posts){
            if(post.getUser() == null){
                continue;
            }
            List<Reactions> rec = reactionsRepository.findAllByPost(post);
            Optional<User> user = userRepository.findById(id);
            User crntUser = user.get();

            if(reactionsRepository.findById(new Reactions.CompositeKey(crntUser, post)).isPresent()){
                post.setIsReact(1);
            }

            post.setNumberOfReacts(rec.size());
            List<Comment> comments = commentRepository.findByPost(post);
            post.setNumberOfComments(comments.size());



        }
        return posts;



//        if(userRepository.findById(userId).isEmpty()){
//            throw new GeneralException("1","User is not found");
//        }
//        Optional<User> crntUser = userRepository.findById(userId);
//        User user = crntUser.get();
//
//        List<Post> posts = postRepository.findAll();
//        if(posts.isEmpty()){
//            return posts;
//        }
//        for(Post post : posts){
//            if(post.getUser() == null){
//                continue;
//            }
//            List<Reactions> rec = reactionsRepository.findAllByPost(post);
//
//            if(reactionsRepository.findById(new Reactions.CompositeKey(user, post)).isPresent()){
//                post.setIsReactedByMe(1);
//            }
//
//            post.setNumberOfReacts(rec.size());
//            List<Comment> comments = commentRepository.findByPost(post);
//            post.setNumberOfComments(comments.size());
//            List<User> friends = post.getUser().getFriends();
//
//
//            if(friends!= null)
//            {
//                if ((post.getPrivacy() == PrivacyEnum.FRIENDS)) {
//                    if (!friends.contains(crntUser)) {
//                        posts.remove(post);
//                    }
//                } else if (post.getPrivacy() == PrivacyEnum.ONLYME) {
//                    posts.remove(post);
//
//                }
//            }
//            else{
//                if(post.getPrivacy() != PrivacyEnum.PUBLIC){
//                    posts.remove(post);
//
//                }
//            }
//
//
//        }
//        return posts;

    }


}

