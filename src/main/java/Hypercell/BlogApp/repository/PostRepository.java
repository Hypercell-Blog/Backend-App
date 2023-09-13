package Hypercell.BlogApp.repository;
import Hypercell.BlogApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.user.id = ?1")
     List<Post> findByUserId(Integer userId);

    @Query("SELECT p FROM Post p WHERE p.shared_post.id = ?1")
    List<Post> findAllByShared_post(int id);
}
