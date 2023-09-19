package Hypercell.BlogApp.service;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Comment;

import java.util.List;

public interface CommentService{
    List<Comment> getComments();
    Comment getComment(Integer commentId) throws GeneralException;
    List<Comment> getCommentByPost(Integer postId) throws GeneralException;
    Comment addComment(Comment comment);
    Comment updateComment(Integer id,Comment comment);
    void deleteComment(Integer id);
}
