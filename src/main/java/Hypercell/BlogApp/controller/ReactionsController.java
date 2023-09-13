package Hypercell.BlogApp.controller;

import Hypercell.BlogApp.model.Post;
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

    @PostMapping("add")
    public Reactions addReactions(@RequestBody Reactions reactions){
        return reactionsService.AddReaction(reactions);
    }

    @GetMapping("getPostReactions")
    public List<Reactions> getReactions(@RequestBody Post post){
        return reactionsService.GetPostReactions(post.getId());
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
