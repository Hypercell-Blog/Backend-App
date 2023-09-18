package Hypercell.BlogApp.controller;
import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/post")
public class postController {

    private postInterface postinterface;

    public postController(postInterface postinterface){
        this.postinterface=postinterface;
    }

    @PostMapping("add/{user-id}")
    public Post addPost(@RequestBody Post post,@PathVariable("user-id") int id ){

        return postinterface.addPost(post, id);
    }

    @PutMapping("update/{post-id}")
    public ResponseEntity updatePost( @RequestBody Post post,@PathVariable ("post-id") int id){
        return new ResponseEntity(postinterface.updatePost(post, id), HttpStatus.ACCEPTED);
    }


    @GetMapping("get")
    public Post getPost(@RequestParam("userId") Integer userId, @RequestParam("postId") Integer postId){
       // return postinterface.getPost(id);
        return postinterface.getPost(userId,postId);
    }

    @DeleteMapping("delete/{post-id}")
    public ResponseEntity deletePost(@PathVariable ("post-id") int id ) throws GeneralException {
        return new ResponseEntity(postinterface.deletePost(id), HttpStatus.OK);
    }

    @GetMapping("getPosts/{user-id}")
    public ResponseEntity getPosts(@PathVariable("user-id") Integer userId) throws GeneralException {
        return new ResponseEntity(postinterface.getPosts(userId), HttpStatus.OK);
    }

}
