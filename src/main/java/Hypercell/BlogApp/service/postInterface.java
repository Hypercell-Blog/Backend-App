package Hypercell.BlogApp.service;

import Hypercell.BlogApp.model.Post;

import java.util.List;

public interface postInterface {
//add delete update get

    Post addPost(Post post, Integer id);
    Post updatePost( Post post,int id);
    boolean deletePost(int id);
    Post getPost (int id);

    List<Post> getPosts(Integer userId);

}
