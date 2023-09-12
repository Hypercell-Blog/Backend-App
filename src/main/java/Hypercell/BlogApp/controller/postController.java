package Hypercell.BlogApp.controller;
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
            Post res =postinterface.updatePost(post, id);
            res.setUser_name(res.getUser().getName());
        return new ResponseEntity(res, HttpStatus.ACCEPTED);
    }


    @GetMapping("get/{post-id}")
    public Post getPost(@PathVariable("post-id") int id){
        return postinterface.getPost(id);
    }

    @DeleteMapping("delete/{post-id}")
    public boolean deletePost(@PathVariable ("post-id") int id ){
        return postinterface.deletePost(id);
    }

    @GetMapping("getPosts/{user-id}")
    public ResponseEntity getPosts(@PathVariable("user-id") Integer userId) {
        return new ResponseEntity(postinterface.getPosts(userId), HttpStatus.OK);
    }

}
