package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.model.Comment;
import Hypercell.BlogApp.repository.CommentRepository;
import Hypercell.BlogApp.repository.PostRepository;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getComment(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->new RuntimeException("Wrong comment id entered"));
    }

    @Override
    public List<Comment> getCommentByPost(Integer postId) {
        return commentRepository.findByPost(postRepository.findById(postId).orElseThrow());
//        return null;
    }

    @Override
    public Comment addComment(Comment comment) {

        comment.setPost(postRepository.findById(comment.getPostId()).orElseThrow());
        comment.setUser(userRepository.findById(comment.getUserId()).orElseThrow());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Integer id, Comment comment) {
        Comment comm=commentRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid comment id entered"));
        comm.setCommentDate(comment.getCommentDate());
        comm.setContent(comment.getContent());
        comm.setPost(postRepository.findById(comment.getPostId()).orElseThrow());
        comm.setUser(userRepository.findById(comment.getUserId()).orElseThrow());
        return commentRepository.save(comm);


    }

    @Override
    public void deleteComment(Integer id) {
        Comment comment=commentRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid comment id entered"));
        commentRepository.delete(comment);
    }
}
