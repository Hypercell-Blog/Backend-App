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
        if(id <0 && !postRepository.existsById(id)){
            throw new RuntimeException("Post is not found");
        } else{
            postRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public Post getPost(int id) {
        if(id <0 && !postRepository.existsById(id)){
            throw new RuntimeException("Id is not found");
        } else{
            return postRepository.findById(id).orElseThrow();
        }
    }

    @Override
    public List<Post> getPosts(Integer userId) {
        if(userId != null){
            return postRepository.findByUserId(userId);
        }
        return null;
    }
}