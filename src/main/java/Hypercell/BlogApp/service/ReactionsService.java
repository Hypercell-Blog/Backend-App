package Hypercell.BlogApp.service;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.Reactions;

import java.util.List;

public interface ReactionsService {

    Reactions AddReaction(Reactions reaction) throws GeneralException;
    Boolean DeleteReaction(int post_id,int user_id) throws GeneralException;
    Reactions UpdateReaction(Reactions reaction,int post_id,int user_id) throws GeneralException;
     List<Reactions> GetPostReactions(Integer postId) throws GeneralException;



}
