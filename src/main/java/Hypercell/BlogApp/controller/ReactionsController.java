package Hypercell.BlogApp.controller;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.Reactions;

import java.util.List;

import Hypercell.BlogApp.model.response.body.GeneralResponse;
import Hypercell.BlogApp.service.ReactionsService;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReactionsController {
    private final ReactionsService reactionsService;
    @Autowired
    private postInterface postService;
    public ReactionsController(ReactionsService reactionsService){
        this.reactionsService=reactionsService;
    }

    @PostMapping("add-react/{user-id}")
    public GeneralResponse<Reactions> addReactions(@RequestBody Reactions reactions, @PathVariable ("user-id") Integer id) throws GeneralException {
        return new GeneralResponse<>(true,reactionsService.AddReaction(reactions));
    }

    @GetMapping("all-react/{post-id}")
    public GeneralResponse<List<Reactions> > getReactions(@PathVariable("post-id") int post_id) throws GeneralException {

        return new GeneralResponse<>(true,reactionsService.GetPostReactions(post_id));
    }

        @PutMapping("update-react/{post-id}/{user-id}")
        public GeneralResponse<Reactions> updateReaction(@RequestBody Reactions reactions,@PathVariable("post-id") int post_id,@PathVariable("user-id") int user_id) throws GeneralException {

            return new GeneralResponse<>(true,reactionsService.AddReaction(reactions));
        }

    @DeleteMapping("delete-react/{post-id}/{user-id}")
    public GeneralResponse<Boolean> deleteReaction(@PathVariable("post-id") int post_id,@PathVariable("user-id") int user_id) throws GeneralException {
        if(reactionsService.GetPostReactions(post_id)==null)
            return new GeneralResponse<>(false,false);

      return new GeneralResponse<>(true,reactionsService.DeleteReaction(post_id,user_id));
    }

}
