package Hypercell.BlogApp.controller;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Comment;
import Hypercell.BlogApp.model.response.body.GeneralResponse;
import Hypercell.BlogApp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }


//    @GetMapping("/get/{comment-id}")
//    public Comment getComment(@PathVariable("comment-id") Integer commentId) throws GeneralException {
//        return commentService.getComment(commentId);
//    }

    @GetMapping("/all-comment/{post-id}")
    public GeneralResponse<List<Comment>> getCommentByPost(@PathVariable("post-id") Integer postId) throws GeneralException {
        return new GeneralResponse<>(true,commentService.getCommentByPost(postId));
    }

    @PostMapping("/add-comment/{post-id}")
    public GeneralResponse<Comment> addComment(@RequestBody Comment comment, @PathVariable("post-id") Integer postId){
        comment.setPostId(postId);
        return new GeneralResponse<>(true,commentService.addComment(comment));
    }


    @PutMapping("/update/{comment-id}")
    public Comment updateComment(@PathVariable("comment-id") Integer id, @RequestBody Comment comment){
        return commentService.updateComment(id,comment);
    }


    @DeleteMapping("/delete-comment/{comment-id}/{user-id}")
    public GeneralResponse<Boolean> deleteComment(@PathVariable("comment-id") Integer id, @PathVariable("user-id") Integer userId) throws GeneralException {
        if(commentService.getComment(id)==null)
            throw new GeneralException("1","Wrong comment id entered");
        if(commentService.getComment(id).getUser().getId()!=userId)
            throw new GeneralException("1","You are not allowed to delete this comment");
        commentService.deleteComment(id);
        return new GeneralResponse<>(true,true);
    }



}
