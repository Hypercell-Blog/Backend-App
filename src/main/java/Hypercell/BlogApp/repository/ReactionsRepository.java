package Hypercell.BlogApp.repository;

import Hypercell.BlogApp.model.Reactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionsRepository extends JpaRepository<Reactions,Reactions.CompositeKey> {

}
