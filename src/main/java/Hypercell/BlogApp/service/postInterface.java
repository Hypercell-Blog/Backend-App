package Hypercell.BlogApp.service;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.PrivacyEnum;
import Hypercell.BlogApp.model.response.body.Response;

import java.util.List;

public interface postInterface {
//add delete update get

    Post addPost(Post post, Integer id);
    Post updatePost( Post post,int id);
    Response deletePost(int id) throws GeneralException;
    Post getPost (int id,int userId,int friendId);

    List<Post> getPosts(Integer userId,Integer friendId) throws GeneralException;

}
