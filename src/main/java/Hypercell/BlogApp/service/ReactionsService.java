package Hypercell.BlogApp.service;

import Hypercell.BlogApp.model.Reactions;

import java.util.List;

public interface ReactionsService {

    Reactions AddReaction(Reactions reaction, int post_id,int user_id);
    Boolean DeleteReaction(int post_id,int user_id);
    Reactions UpdateReaction(Reactions reaction,int post_id,int user_id);
     List<Reactions> GetPostReactions();



}
