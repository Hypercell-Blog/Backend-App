package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.repository.PostRepository;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post, int id) {
        if(id <0 && !postRepository.existsById(id)){
            throw new RuntimeException("Post is not found");
        } else{
            post.setId(id);
            return postRepository.saveAndFlush(post);
        }
    }

    @Override
    public boolean deletePost(int id) {
        if(id <0 || !postRepository.existsById(id)){
            return false;
//            throw new RuntimeException("Post is not found");
        } else{
            postRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public Post getPost(int id) {
        if(id <0 || !postRepository.existsById(id)){
            throw new RuntimeException("Id is not found");
        } else{
            Post post = postRepository.findById(id).orElseThrow();
            post.setUser_name(post.getUser().getName());
            return post;
        }
    }

    @Override
    public List<Post> getPosts(Integer userId) {
        if(userRepository.findById(userId).isEmpty()){
            return null;

        }
        User user = userRepository.findById(userId).orElseThrow();
        List<Post> result= postRepository.findByUserId(userId);
        for(int i =  0 ; i < result.size(); i++){
            result.get(i).setUser_name(user.getName());
        }
        return result;


    }
}
