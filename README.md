# Backend App for Blog Website 

This is a backend application developed using Spring Boot for a feature-rich blog website. The application supports user sign-up, login, profile updates, post creation, commenting, and post reactions. It leverages the power of Spring Boot to provide a reliable and scalable backend infrastructure. Additionally, the application includes Swagger documentation for easy API exploration and testing.

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

The backend application provides the following features for the blog website:

- **User Sign-up**: Allow users to register and create an account.
- **User Login**: Enable users to authenticate and log into their accounts.
- **Profile Update**: Allow users to update their profile information, such as username, email, and profile picture.
- **Post Creation**: Enable users to create and publish blog posts, including the title, content, and optional images.
- **Commenting**: Allow users to add comments to blog posts, facilitating engagement and discussions.
- **Post Reactions**: Enable users to react to blog posts by expressing their emotions, such as liking or disliking.
- **Swagger Documentation**: Explore and interact with the application's APIs using the Swagger documentation available at [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/).

## API Endpoints

The backend application exposes the following API endpoints:

- User-related endpoints:
  - `POST /api/signup`: Register a new user.
  - `POST /api/login`: Authenticate and log in a user.
  - `PUT /api/profile`: Update the user's profile.

- Post-related endpoints:
  - `POST /api/posts`: Create a new blog post.
  - `PUT /api/posts/{postId}`: Update an existing blog post.

- Comment-related endpoints:
  - `POST /api/posts/{postId}/comments`: Add a comment to a blog post.
  - `PUT /api/posts/{postId}/comments/{commentId}`: Update a comment.

- Reaction-related endpoints:
  - `POST /api/posts/{postId}/reactions`: Add a reaction to a blog post.

Please refer to the Swagger documentation for detailed information about each endpoint's request and response structures.

## Conclusion

This backend application, developed using Spring Boot, provides a robust and feature-rich infrastructure for a blog website. With functionality for user sign-up, login, profile updates, post creation, commenting, and post reactions, it offers a comprehensive experience for users. The included Swagger documentation further simplifies API exploration and testing. Feel free to explore and extend the application based on your specific requirements.
