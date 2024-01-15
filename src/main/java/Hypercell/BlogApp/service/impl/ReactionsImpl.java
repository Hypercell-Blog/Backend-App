package Hypercell.BlogApp.service.impl;

import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.Reactions;
import Hypercell.BlogApp.model.User;
import Hypercell.BlogApp.repository.PostRepository;
import Hypercell.BlogApp.repository.ReactionsRepository;
import Hypercell.BlogApp.repository.UserRepository;
import Hypercell.BlogApp.service.ReactionsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionsImpl implements ReactionsService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReactionsRepository reactionsRepository;

    public ReactionsImpl(PostRepository postRepository, UserRepository userRepository, ReactionsRepository reactionsRepository) {
        this.reactionsRepository = reactionsRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Reactions AddReaction(Reactions reaction) throws GeneralException {
        if (reaction.getPostId() < 0 || !postRepository.existsById(reaction.getPostId()) || reaction.getUserId() < 0 || !userRepository.existsById(reaction.getUserId())) {
            throw new GeneralException("1", "ID IS NOT FOUND");
        }

        User user=userRepository.findById(reaction.getUserId()).orElseThrow();
        Post post =postRepository.findById(reaction.getPostId()).orElseThrow();
        reaction.setPost(post);
        reaction.setUser(user);
        reaction.setReaction_date(java.time.LocalDate.now().toString());


            return reactionsRepository.save(reaction);

    }

    @Override
    public Boolean DeleteReaction(int post_id, int user_id) throws GeneralException {
        if (post_id < 0 || !postRepository.existsById(post_id) || user_id < 0 || !userRepository.existsById(user_id)) {
            throw  new GeneralException("1","ID IS NOT FOUND");
        }

        User user = userRepository.findById(user_id).orElseThrow();
        Post post = postRepository.findById(post_id).orElseThrow();
        if(reactionsRepository.findById(new Reactions.CompositeKey(user, post)).isEmpty()){
            throw new GeneralException("1","this reaction is not found");
        }

        reactionsRepository.deleteById(new Reactions.CompositeKey(user, post));
        return true;
    }

    @Override
    public Reactions UpdateReaction(Reactions reaction, int post_id, int user_id) throws GeneralException {

        if (post_id < 0 || !postRepository.existsById(post_id) || user_id < 0 || !userRepository.existsById(user_id) ){
            throw new GeneralException("1","ID IS NOT FOUND");

        } else {
            reaction.setUserId(user_id);
            reaction.setPostId(post_id);
            return reactionsRepository.saveAndFlush(reaction);
        }

    }
    @Override
    public List<Reactions> GetPostReactions(Integer postId) throws GeneralException {
    if(!postRepository.existsById(postId)){
            throw new GeneralException("1","ID IS NOT FOUND");
    }
        return reactionsRepository.findAllByPost(postRepository.findById(postId).orElseThrow());
    }
}
