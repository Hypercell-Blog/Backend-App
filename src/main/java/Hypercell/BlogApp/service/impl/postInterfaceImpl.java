package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.model.response.body.Response;
import Hypercell.BlogApp.repository.PostRepository;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.List;

@Service
public class postInterfaceImpl implements postInterface {

    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    public postInterfaceImpl(PostRepository postrepository){
        this.postRepository=postrepository;
    }


    @Override
    public Post addPost(Post post, Integer id) {
        User user = userRepository.findById(id).orElseThrow();

        // here if the post is shared post we will get the original post and set it to the shared post
        if(post.getShared_post_id() != null) {
            Post shared_post = postRepository.findById(post.getShared_post_id()).orElseThrow();
            if (shared_post.getShared_post() != null) {
                shared_post = shared_post.getShared_post();
                System.out.println("shared post is not null");
            }
            post.setShared_post(shared_post);
        }
        if(post.getPost_date() == null){
            post.setPost_date(java.time.LocalDate.now().toString());
        }

        post.setUser(user);
        return postRepository.save(post);
    }


    @Override
    public Post updatePost(Post post, int id) {
        if(id <0 || !postRepository.existsById(id)){
            throw new RuntimeException("Post is not found");
        } else{
            post.setId(id);
            return postRepository.saveAndFlush(post);
        }
    }

    @Override
    public Response deletePost(int id) throws GeneralException {
        
        if(id <0 || !postRepository.existsById(id)){
            throw new GeneralException("1", "Post Not Found");
        } else {

            List<Post> posts = postRepository.findAllByShared_post(id);
            for (int i = 0; i < posts.size(); i++) {
                postRepository.delete(posts.get(i));
            }
            postRepository.deleteById(id);
            return new Response("0", "Post Deleted Successfully");

        }
    }

    @Override
    public Post getPost(int id) {
        if(id <0 || !postRepository.existsById(id)){
            throw new RuntimeException("Id is not found");
        } else{
            Post post = postRepository.findById(id).orElseThrow();
            post.setUser_name(post.getUser().getName());
            if(post.getShared_post()!= null){
                post.getShared_post().setUser_name(post.getShared_post().getUser().getName());
            }
            return post;
        }
    }

    @Override
    public List<Post> getPosts(Integer userId) throws GeneralException {
        if(userRepository.findById(userId).isEmpty()){
            throw new GeneralException("1","User is not found");

        }
        User user = userRepository.findById(userId).orElseThrow();
        List<Post> result= postRepository.findByUserId(userId);
        for(int i =  0 ; i < result.size(); i++){
            result.get(i).setUser_name(user.getName());
            if(result.get(i).getShared_post()!= null){
                result.get(i).getShared_post().setUser_name(result.get(i).getShared_post().getUser().getName());
            }
        }
        return result;


    }
}
