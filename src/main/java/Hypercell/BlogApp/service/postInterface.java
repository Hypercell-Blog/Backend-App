package Hypercell.BlogApp.service;

import Hypercell.BlogApp.model.Post;

public interface postInterface {
//add delete update get

    Post addPost(Post post);
    Post updatePost( Post post,int id);
    boolean deletePost(int id);
    Post getPost (int id);

}
