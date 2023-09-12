package Hypercell.BlogApp.repository;

import Hypercell.BlogApp.model.Comment;
import Hypercell.BlogApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

//    @Query("SELECT * FROM Comment c, Post p WHERE c.post_id =p.id ")
//    List<Comment> findByPostId(Integer postId);

    List<Comment> findByPost(Post post);
}
