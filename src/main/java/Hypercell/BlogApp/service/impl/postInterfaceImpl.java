package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.repository.postRepository;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.stereotype.Service;

@Service
public class postInterfaceImpl implements postInterface {

    private postRepository postrepository;

    public postInterfaceImpl(postRepository postrepository){
        this.postrepository=postrepository;
    }


    @Override
    public Post addPost(Post post) {
        return postrepository.save(post);
    }

    @Override
    public Post updatePost(Post post, int id) {
        if(id <0 && !postrepository.existsById(id)){
            throw new RuntimeException("Post is not found");
        } else{
            post.setId(id);
            return postrepository.saveAndFlush(post);
        }
    }

    @Override
    public boolean deletePost(int id) {
        if(id <0 && !postrepository.existsById(id)){
            throw new RuntimeException("Post is not found");
        } else{
            postrepository.deleteById(id);
            return true;
        }
    }

    @Override
    public Post getPost(int id) {
        if(id <0 && !postrepository.existsById(id)){
            throw new RuntimeException("Id is not found");
        } else{
            return postrepository.findById(id).orElseThrow();
        }
    }
}
