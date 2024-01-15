package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Comment;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.repository.CommentRepository;
import Hypercell.BlogApp.repository.PostRepository;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
    public Comment getComment(Integer commentId) throws GeneralException {

        return commentRepository.findById(commentId).orElseThrow(()->new GeneralException("1","Wrong comment id entered"));
    }

    String getImage(User user){
        if(user.getPic() == null)
            return null;

        Path path = Paths.get(user.getPic());
        File file = new File(path.toAbsolutePath().toString());
        String base64Image="";
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                base64Image += myReader.nextLine();
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return base64Image;
    }

    @Override
    public List<Comment> getCommentByPost(Integer postId) throws GeneralException {
        if(!postRepository.existsById(postId))
            throw new GeneralException("1","Wrong post id entered");
        List<Comment> comments = commentRepository.findByPost(postRepository.findById(postId).orElseThrow());
        Map<Integer, String> images = new HashMap<>();
        for (Comment comment : comments){
            if(comment.getUser().getPic() != null)
            {
                if (!images.containsKey(comment.getUserId()))
                    images.put(comment.getUserId(), getImage(comment.getUser()));

                comment.getUser().setPic(images.get(comment.getUserId()));
            }
        }

        return  comments;
//        return null;
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCommentDate(String.valueOf(java.time.LocalDate.now()));
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
