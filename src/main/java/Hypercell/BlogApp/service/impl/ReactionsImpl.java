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
        System.out.println("here user id");
        System.out.println(reaction.getUserId());
        User user=userRepository.findById(reaction.getUserId()).orElseThrow();
        Post post =postRepository.findById(reaction.getPostId()).orElseThrow();
        reaction.setPost(post);
        reaction.setUser(user);
        reaction.setReaction_date(java.time.LocalDate.now().toString());
        String emo = reaction.getEmoji().toLowerCase();
        if (!reaction.getEmos().contains(emo)) {
            throw new GeneralException("1", "invalid react");
        } else {

            return reactionsRepository.save(reaction);
        }
    }

    @Override
    public Boolean DeleteReaction(int post_id, int user_id) {
        if (post_id < 0 || !postRepository.existsById(post_id) || user_id < 0 || !userRepository.existsById(user_id)) {
            return false;
        }
        User user = userRepository.findById(user_id).orElseThrow();
        Post post = postRepository.findById(post_id).orElseThrow();
        reactionsRepository.deleteById(new Reactions.CompositeKey(user, post));
        return true;
    }

    @Override
    public Reactions UpdateReaction(Reactions reaction, int post_id, int user_id) {
        String emo = reaction.getEmoji().toLowerCase();
        if (post_id < 0 || !postRepository.existsById(post_id) || user_id < 0 || !userRepository.existsById(user_id) ||
                !reaction.getEmos().contains(emo)) {
            throw new RuntimeException("ID IS NOT FOUND");

        } else {
            reaction.setUserId(user_id);
            reaction.setPostId(post_id);
            return reactionsRepository.saveAndFlush(reaction);
        }

    }
    @Override
    public List<Reactions> GetPostReactions(Integer postId) {
        return reactionsRepository.findAllByPost(postRepository.findById(postId).orElseThrow());
    }
}
