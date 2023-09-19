package Hypercell.BlogApp.repository;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.Reactions;
import Hypercell.BlogApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReactionsRepository extends JpaRepository<Reactions,Reactions.CompositeKey> {
//    @Query("select * from reactions where post_id = ?1")
    List<Reactions> findAllByPost(Post post);




    List<Reactions> findAllByUser(User user);

}
