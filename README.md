# Backend App for Blog Website
This is a backend application developed using Spring Boot for a blog website. It provides various features to enhance user experience, including user management, profile management, post management, comment functionality, and post reactions. This readme file aims to provide an overview and guide for the application.

## Prerequisites

Before running the application, ensure that you have the following prerequisites installed:

- Java Development Kit (JDK) - Version 17 or higher
- Spring Boot - Latest version
- Maven - Latest version
- Your preferred Integrated Development Environment (IDE) - IntelliJ IDEA, Eclipse, etc.

## Setup

1. Clone the repository to your local machine.
   
2. Open the project in your preferred IDE.

3. Configure the application properties:
   - Open the `application.properties` file located in the `src/main/resources` directory.
   - Customize the database connection settings such as URL, username, and password according to your database setup.

4. Build the project:
   - Use Maven to build the project by running the following command in the terminal or command prompt:
     ```
     mvn clean install
     ```

## Database Setup

1. Create a database for the application in your preferred database management system (MySQL, PostgreSQL, etc.).

2. Update the database configuration in the `application.properties` file with the appropriate database URL, username, and password.

3. Run the application:
   - Use the following command to run the application:
     ```
     mvn spring-boot:run
     ```

## Features

### User Management

- **Sign Up**: Users can create an account by providing necessary details such as username, email, and password.
- **Log In**: Registered users can log in using their credentials to access their account.

### Profile Management

- **Update Profile**: Logged-in users can update their profile information, including username, email, and profile picture.

### Post Management

- **Add Post**: Authenticated users can create and publish blog posts by providing the title, content, and optional images.
- **Edit Post**: Users can edit their own posts, updating the title, content, or images.
- **Delete Post**: Users can delete their own posts.

### Comment Functionality

- **Add Comment**: Users can add comments to blog posts, sharing their thoughts and engaging in discussions.
- **Edit Comment**: Users can edit their own comments if necessary.
- **Delete Comment**: Users can delete their own comments.

### Post Reactions

- **Add Reaction**: Users can react to blog posts by liking, disliking, or expressing other emotions.
- **View Reactions**: Users can see the total count and individual reactions (likes, dislikes, etc.) on each post.

## API Endpoints

The backend application provides the following API endpoints:

- `POST /api/signup`: Register a new user.
- `POST /api/login`: Authenticate and log in a user.
- `PUT /api/profile`: Update the user's profile.
- `POST /api/posts`: Create a new blog post.
- `PUT /api/posts/{postId}`: Update an existing blog post.
- `DELETE /api/posts/{postId}`: Delete a blog post.
- `POST /api/posts/{postId}/comments`: Add a comment to a blog post.
- `PUT /api/posts/{postId}/comments/{commentId}`: Update a comment.
- `DELETE /api/posts/{postId}/comments/{commentId}`: Delete a comment.
- `POST /api/posts/{postId}/reactions`: Add a reaction to a blog post.
- `GET /api/posts/{postId}/reactions`: Get all reactions for a blog post.

Please refer to the API documentation or code comments for more details on request/response structures and data formats.

## Conclusion

This backend application built with Spring Boot provides essential features for a blog website, including user management, profile management, post management, commenting functionality, and post reactions. Feel free to explore and extend the functionality according to your specific requirements.
