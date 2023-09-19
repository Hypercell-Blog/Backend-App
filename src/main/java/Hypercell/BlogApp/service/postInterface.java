package Hypercell.BlogApp.service;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.PrivacyEnum;
import Hypercell.BlogApp.model.response.body.Response;

import java.io.IOException;
import java.util.List;

public interface postInterface {
//add delete update get

    Post addPost(Post post, Integer id);
    Post updatePost( Post post,int id) throws GeneralException;
    Response deletePost(int id) throws GeneralException;
    Post getPost (int id,int userId,int friendId) throws GeneralException;  //friendId

    List<Post> getPosts(Integer userId,Integer friendId) throws GeneralException;

    Post getPost(int id);

    List<Post> getPosts(Integer userId) throws GeneralException;

    String uploadPicture(String image,int postId) throws IOException;
    boolean deletePicture(int postId) throws IOException;
}
