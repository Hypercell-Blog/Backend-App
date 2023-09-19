//package Hypercell.BlogApp.model.response.body;
//
//import Hypercell.BlogApp.model.Post;
//import Hypercell.BlogApp.model.User;
//import lombok.Data;
//
//import java.beans.ConstructorProperties;
//
//@Data
//@ConstructorProperties
//public class GetPostResponse {
//   int id;
//   String content;
//
//   String image;
//
//   Post sharePost;
//
//    String createdAt;
//
//    String title;
//
//    User user;
//
//    int numOfComments;
//
//    int numOfReacts;
//
//    int isReactedByMe;
//
//    public GetPostResponse(Post post) {
//        this.id = post.getId();
//        this.content = post.getContent();
//        this.image = post.getImage();
//        this.sharePost = post.getShared_post();
//        this.createdAt = post.getCreateAt();
//        this.title = post.getTitle();
//        this.user = post.getUser();
//    }
//}
