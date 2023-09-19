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

    @PostMapping("add-post{user-id}")
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

    @GetMapping("posts/{user-id}")
    public GeneralResponse<List<Post>> getPosts(@PathVariable("user-id") int id)
    throws GeneralException {

        GeneralResponse<List<Post>> res = new GeneralResponse<>();
        res.setData(postinterface.posts(id));
        res.setSuccess(true);
//        res.
        return res;  //friendId
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

    @GetMapping("all-post/{user-id}")
    public GeneralResponse<List<Post> > getAllPost(@PathVariable("user-id") int id ) throws GeneralException {
        List<Post> posts = postinterface.getAllPosts(id);
        GeneralResponse<List<Post> > res = new GeneralResponse<>();
        res.setSuccess(true);
        res.setData(posts);
        return res;
    }


   



}
