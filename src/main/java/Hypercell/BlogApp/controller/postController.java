package Hypercell.BlogApp.controller;
import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
public class postController {

    private final postInterface postinterface;

    public postController(postInterface postinterface){
        this.postinterface=postinterface;
    }

    @PostMapping("add/{user-id}")
    public Post addPost(@RequestBody Post post,@PathVariable("user-id") int id ){

        return postinterface.addPost(post, id);
    }

    @PutMapping("update/{post-id}")
    public ResponseEntity<?> updatePost( @RequestBody Post post,@PathVariable ("post-id") int id) throws GeneralException {
        return new ResponseEntity<>(postinterface.updatePost(post, id), HttpStatus.ACCEPTED);
    }


    @GetMapping("get")
    public Post getPost(@RequestParam("post-id") int id,@RequestParam("user-id")
                        int userId,@RequestParam("friend-id") int friendId) throws GeneralException {
        return postinterface.getPost(id,userId,friendId);   //friendId
    }

    @GetMapping("getPosts")
    public List<Post> getPosts(@RequestParam("user-id")
    int userId) throws GeneralException {
        return postinterface.getPosts(userId);  //friendId
    }


    @GetMapping("get/{post-id}")
    public Post getPost(@PathVariable("post-id") int id){

        return postinterface.getPost(id);
    }


    @DeleteMapping("delete/{post-id}")
    public ResponseEntity<?> deletePost(@PathVariable ("post-id") int id ) throws GeneralException {
        return new ResponseEntity<>(postinterface.deletePost(id), HttpStatus.OK);
    }

    @GetMapping("getFriendPosts")
    public ResponseEntity<?> getPosts(@RequestParam("user-id") Integer userId,
                                   @RequestParam("friend-id") Integer friendId) throws GeneralException {
        return new ResponseEntity<>(postinterface.getPosts(userId,friendId), HttpStatus.OK);
    }

    @PostMapping("upload-image")
    public String uploadImage(@RequestParam("image") String image,@RequestParam("postId") Integer postId){
        String imagePath;
        try{
            imagePath=postinterface.uploadPicture(image,postId);
            return imagePath;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    @DeleteMapping("delete-image/{postId}")
    public boolean deleteImage(@PathVariable("postId") Integer postId){
        boolean isDeleted=false;
        try{
            isDeleted=postinterface.deletePicture(postId);
            return isDeleted;
        }
        catch(Exception exception){
            exception.printStackTrace();
            return isDeleted;
        }

    }

}
