package Hypercell.BlogApp.controller;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class postController {

    private postInterface postinterface;

    public postController(postInterface postinterface){
        this.postinterface=postinterface;
    }

    @PostMapping("add")
    public Post addPost(@RequestBody Post post){
        return postinterface.addPost(post);
    }

    @PutMapping("update/{post-id}")
    public ResponseEntity updatePost( @RequestBody Post post,@PathVariable ("post-id") int id){
        return new ResponseEntity(postinterface.updatePost(post, id), HttpStatus.ACCEPTED);
    }


    @GetMapping("get/{post-id}")
    public Post getPost(@PathVariable("post-id") int id){
        return postinterface.getPost(id);
    }

    @DeleteMapping("delete/{post-id}")
    public boolean deletePost(@PathVariable ("post-id") int id ){
        return postinterface.deletePost(id);
    }
}
