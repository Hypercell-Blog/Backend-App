package Hypercell.BlogApp.controller;

import Hypercell.BlogApp.model.Comment;
import Hypercell.BlogApp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }

    @GetMapping()
    public List<Comment> getComments(){

        return commentService.getComments();
    }

    @GetMapping("/get/{comment-id}")
    public Comment getComment(@PathVariable("comment-id") Integer commentId){
        return commentService.getComment(commentId);
    }

    @GetMapping("/get/post/{post-id}")
    public List<Comment> getCommentByPost(@PathVariable("post-id") Integer postId){
        return commentService.getCommentByPost(postId);
    }

    @PostMapping("/add")
    public Comment addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }


    @PutMapping("/update/{comment-id}")
    public Comment updateComment(@PathVariable("comment-id") Integer id, @RequestBody Comment comment){
        return commentService.updateComment(id,comment);
    }


    @DeleteMapping("/delete/{comment-id}")
    public void deleteComment(@PathVariable("comment-id") Integer id){
        commentService.deleteComment(id);
    }



}
