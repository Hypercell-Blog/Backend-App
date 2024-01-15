package Hypercell.BlogApp.controller;
import Hypercell.BlogApp.exceptions.GeneralException;
import Hypercell.BlogApp.model.Post;
import Hypercell.BlogApp.model.response.body.GeneralResponse;
import Hypercell.BlogApp.service.postInterface;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
public class postController {

    private final postInterface postinterface;

    public postController(postInterface postinterface){
        this.postinterface=postinterface;
    }

    @DeleteMapping("delete-post")
    public boolean deletePost(@RequestParam("post-id") int id) throws GeneralException {
        return postinterface.deletePost(id);
    }

    @PostMapping("add-post/{user-id}")
    public Post addPost(@RequestBody Post post,@PathVariable("user-id") int id ) throws IOException, GeneralException {
        System.out.println(post.getImage());
        String image = post.getImage();
        post.setImage("");
        Post res = postinterface.addPost(post,id);
        res.setImage(postinterface.uploadPicture(image,post.getId()));

        return res;
    }

    @PutMapping("update/{post-id}/{user-id}")
    public GeneralResponse<Post> updatePost(@RequestBody Post post, @PathVariable ("post-id") int id, @PathVariable ("user-id") int userId) throws GeneralException {
        if(userId != post.getUser().getId()) {
            throw new GeneralException("1", "invalid user");
        }
        GeneralResponse<Post> res = new GeneralResponse<>();
        res.setData(postinterface.updatePost(post, id));
        res.setSuccess(true);
        return res;
    }


//    @GetMapping("get")
//    public Post getPost(@RequestParam("post-id") int id,@RequestParam("user-id")
//                        int userId,@RequestParam("friend-id") int friendId) throws GeneralException {
//        return postinterface.getPost(id,userId,friendId);   //friendId
//    }

    @GetMapping("posts/{friend-id}/{user-id}")
    public GeneralResponse<List<Post>> getPosts(@PathVariable("user-id") int userId, @PathVariable("friend-id") int friendId
    ) throws GeneralException {

            GeneralResponse<List<Post>> res = new GeneralResponse<>();
            res.setData(postinterface.getPosts(userId, friendId));
            res.setSuccess(true);


            return res;
    }

    @GetMapping("post-details/{post-id}")
    public  GeneralResponse<Post> getPost(@PathVariable("post-id") int id){

        return new GeneralResponse<>(true,postinterface.getPost(id));
    }


    @DeleteMapping("delete-post/{post-id}/{user-id}")
    public GeneralResponse<Boolean> deletePost(@PathVariable ("post-id") int id, @PathVariable ("user-id") int userId ) throws GeneralException {
        Post post = postinterface.getPost(id);
        if(userId != post.getUser().getId()) {
            throw new GeneralException("1", "invalid user");
        }
        GeneralResponse<Boolean> res = new GeneralResponse<>();
        res.setData(postinterface.deletePost(id));
        res.setSuccess(true);
        return res;
    }

//    @GetMapping("getFriendPosts")
//    public ResponseEntity<?> getPosts(@RequestParam("user-id") Integer userId,
//                                   @RequestParam("friend-id") Integer friendId) throws GeneralException {
//        return new ResponseEntity<>(postinterface.getPosts(userId,friendId), HttpStatus.OK);
//    }

    @GetMapping("all-posts/{user-id}")
    public GeneralResponse<List<Post> > getAllPost(@PathVariable("user-id") int id ) throws GeneralException {
        List<Post> posts = postinterface.getAllPosts(id);
        GeneralResponse<List<Post> > res = new GeneralResponse<>();
        res.setSuccess(true);
        res.setData(posts);
        return res;
    }

    @PostMapping("share-post/{post-id}/{user-id}")
    public GeneralResponse<Post> sharePost(@RequestBody Post post,@PathVariable("post-id") int id,@PathVariable("user-id") int userId) throws GeneralException {
        post.setSharedPostId(id);
        GeneralResponse<Post> re = new GeneralResponse<>();
        re.setData(postinterface.addPost(post,userId));
        re.setSuccess(true);
        return re;
    }




    @PostMapping("upload-image")
    public String uploadImage(@RequestParam("image") String image,@RequestParam("postId") Integer postId){
        String imagePath;
        try{
            imagePath=postinterface.uploadPicture(image,postId);
            return imagePath;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    @DeleteMapping("delete-image/{postId}")
    public boolean deleteImage(@PathVariable("postId") Integer postId){
        boolean isDeleted=false;
        try{
            isDeleted=postinterface.deletePicture(postId);
            return isDeleted;
        }
        catch(Exception exception){
            exception.printStackTrace();
            return isDeleted;
        }

    }


//    @GetMapping("get/{post-id}")

}
