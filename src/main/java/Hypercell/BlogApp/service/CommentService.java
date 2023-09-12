package Hypercell.BlogApp.service;

import Hypercell.BlogApp.model.Comment;

import java.util.List;

public interface CommentService{
    List<Comment> getComments();
    Comment getComment(Integer commentId);
    List<Comment> getCommentByPost(Integer postId);
    Comment addComment(Comment comment);
    Comment updateComment(Integer id,Comment comment);
    void deleteComment(Integer id);
}
