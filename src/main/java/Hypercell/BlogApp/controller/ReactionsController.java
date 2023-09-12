package Hypercell.BlogApp.controller;

import Hypercell.BlogApp.model.Reactions;

import java.util.List;

import Hypercell.BlogApp.service.ReactionsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Reactions")
public class ReactionsController {
    private ReactionsService reactionsService;
    public ReactionsController(ReactionsService reactionsService){
        this.reactionsService=reactionsService;
    }

    @PostMapping("add/{post-id}/{user-id}")
    public Reactions addReactions(@RequestBody Reactions reactions, @PathVariable("post-id") int post_id,@PathVariable("user-id") int user_id){
        return reactionsService.AddReaction(reactions,post_id,user_id);
    }

    @GetMapping("getPosts")
    public List<Reactions> getReactions(){
        return reactionsService.GetPostReactions();
    }

    @PutMapping("update/{post-id}/{user-id}")
    public Reactions updateReaction(@RequestBody Reactions reactions,@PathVariable("post-id") int post_id,@PathVariable("user-id") int user_id){
        return reactionsService.UpdateReaction(reactions,post_id,user_id);
    }

    @DeleteMapping("delete/{post-id}/{user-id}")
    public boolean deleteReaction(@PathVariable("post-id") int post_id,@PathVariable("user-id") int user_id){
      return reactionsService.DeleteReaction(post_id,user_id);
    }
}
