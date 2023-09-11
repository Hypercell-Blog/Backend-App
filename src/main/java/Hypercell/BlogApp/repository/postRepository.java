package Hypercell.BlogApp.repository;
import Hypercell.BlogApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface postRepository extends JpaRepository<Post, Integer> {
}
