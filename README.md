# CODERS - Social Media App

## CHECKT IT OUT
Register your account, create a post, find a friend, and comment on their post. 
Use this link [CODERS SOCIAL MEDIA APP](http://coder-025.eu-central-1.elasticbeanstalk.com/login-form) -

Scan the QR code below to access the app on your mobile device.

![QRCodeCoders](src/main/resources/img/QRCodeCoders.png)


## Overview



This is a social media application built with Spring Boot and Thymeleaf. Users can register, create posts, comment on posts, like posts and comments, and manage friendships. The application also includes an admin dashboard for managing users, posts, and comments.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Setup](#setup)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- User registration and authentication
- CRUD operations for posts, comments, and likes
- Friendship management
- Admin dashboard for managing users, posts, and comments
- Real-time updates and notifications

## Technologies

- Java
- Spring Boot
- Spring Security
- Thymeleaf
- Hibernate (JPA)
- MySQL

## Setup

### Prerequisites

- JDK 11 or higher
- Maven
- MySQL

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/A3M-SocialMediaApp/a3m.git
    cd a3m
    ```

2. Set up the MySQL database:
    ```sql
    CREATE DATABASE a3m;
    ```

3. Configure the application properties:
    Update the `src/main/resources/application.properties` file with your MySQL database credentials.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/a3m
    spring.datasource.username=a3m
    spring.datasource.password=a3m
    spring.jpa.hibernate.ddl-auto=update
    ```

4. Build and run the application:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

5. Access the application at `http://localhost:5000`.

## Database Diagram

EER Diagram
Visual representation of the Entity-Relationship for the database schema.

![eer.png](src/main/resources/img/eer.png)

## Project Structure

### Controllers

- `AdminControllerMVC.java`: Handles admin-related operations like managing users, posts, and comments.
- `CommentControllerMVC.java`: Manages CRUD operations for comments.
- `FriendshipControllerMVC.java`: Manages friendships and friendship invitations.
- `HomeControllerMVC.java`: Redirects to the login success page.
- `LikeController.java`: Manages like and unlike operations for posts and comments.
- `LoginControllerMVC.java`: Handles user login and logout operations.
- `MemberControllerMVC.java`: Manages member profiles and their related operations.
- `PostControllerMVC.java`: Handles CRUD operations for posts.
- `UserControllerMVC.java`: Manages user-related operations like registration and profile updates.

### Entities

- `Admin.java`: Represents an admin user.
- `Authority.java`: Represents user roles and permissions.
- `Comment.java`: Represents a comment on a post.
- `FriendshipInvitation.java`: Represents a friendship invitation between members.
- `Like.java`: Represents a like on a post or comment.
- `Member.java`: Represents a member with additional profile details.
- `Post.java`: Represents a post created by a member.
- `User.java`: Represents a user in the application.

### Repositories

- `AdminRepository.java`: Repository for admin-related database operations.
- `AuthorityRepository.java`: Repository for authority-related database operations.
- `CommentRepository.java`: Repository for comment-related database operations.
- `FriendshipInvitationRepository.java`: Repository for friendship invitation-related database operations.
- `LikeRepository.java`: Repository for like-related database operations.
- `MemberRepository.java`: Repository for member-related database operations.
- `PostRepository.java`: Repository for post-related database operations.
- `UserRepository.java`: Repository for user-related database operations.

### Services

- `AdminService.java`: Contains business logic for admin operations.
- `AdminServiceImpl.java`: Implementation of `AdminService`.
- `AuthorityService.java`: Contains business logic for authority operations.
- `AuthorityServiceImpl.java`: Implementation of `AuthorityService`.
- `BadWordsFilterService.java`: Filters obscene language in comments and posts.
- `CommentService.java`: Contains business logic for comment operations.
- `CommentServiceImpl.java`: Implementation of `CommentService`.
- `FriendshipService.java`: Contains business logic for friendship operations.
- `FriendshipServiceImpl.java`: Implementation of `FriendshipService`.
- `LikeService.java`: Contains business logic for like operations.
- `LikeServiceImpl.java`: Implementation of `LikeService`.
- `MemberService.java`: Contains business logic for member operations.
- `MemberServiceImpl.java`: Implementation of `MemberService`.
- `PostService.java`: Contains business logic for post operations.
- `PostServiceImpl.java`: Implementation of `PostService`.
- `UserService.java`: Contains business logic for user operations.
- `UserServiceImpl.java`: Implementation of `UserService`.

### Security

- `CustomAuthenticationFailureHandler.java`: Custom handler for authentication failures.
- `CustomAuthenticationSuccessHandler.java`: Custom handler for authentication successes.
- `WebSecurityConfig.java`: Configures web security settings including authentication and authorization.

### Exception Handling

- `GlobalExceptionHandler.java`: Handles global exceptions in the application.
- `UserNotFoundException.java`: Custom exception for handling user not found scenarios.

### Database Initialization

- `DatabaseLoader.java`: Initializes the database with initial data for admin and members.

### Frontend

- `styles-grey.css`: Grey-themed styles for the application.
- `styles-lila.css`: Lila-themed styles for the application.
- `cards.html`: HTML template for displaying cards.
- `footer.html`: Footer template.
- `head.html`: Head template.
- `header.html`: Header template.
- `navbar.html`: Navbar template.
- `admin-dashboard.html`: Admin dashboard template.
- `comments-list.html`: Template for listing comments.
- `members-list.html`: Template for listing members.
- `posts-list.html`: Template for listing posts.
- `comment-error.html`: Template for displaying comment errors.
- `comment-form.html`: Template for comment form.
- `comment-info.html`: Template for displaying comment information.
- `comments.html`: Template for displaying comments.
- `error.html`: Template for general error page.
- `error-unable.html`: Template for blocked user error page.
- `friends.html`: Template for displaying friends list.
- `friendship-invitations.html`: Template for displaying friendship invitations.
- `home.html`: Home page template.
- `login-form.html`: Login form template.
- `login-success.html`: Login success page template.
- `member-change-password.html`: Template for changing member password.
- `member-error.html`: Template for member error page.
- `member-form.html`: Template for member registration and update form.
- `member-info.html`: Template for displaying member information.
- `members.html`: Template for displaying all members.
- `post-error.html`: Template for post error page.
- `post-form.html`: Template for creating and updating posts.
- `post-info.html`: Template for displaying post information.
- `posts-of-friends.html`: Template for displaying friends' posts.
- `posts-your.html`: Template for displaying user's posts.

### Configuration Files

- `application.properties`: Configuration file for application properties.
- `pom.xml`: Maven Project Object Model (POM) file.

#### Dependencies

- Spring Boot Actuator
- Spring Boot Data JPA
- Spring Boot Thymeleaf
- Spring Boot Validation
- Spring Boot Web
- MySQL Connector
- Spring Boot Test
- Spring Boot Security
- Thymeleaf Extras Spring Security 6
- Spring Boot Data REST

## Usage

### User Registration

1. Navigate to `/mvc/member-form` to register a new member.
2. Fill in the required details and submit the form.

### Creating and Managing Posts

1. Navigate to `/mvc/posts-your` to view your posts.
2. Use the form to create new posts.
3. Edit or delete existing posts using the provided options.

### Managing Friendships

1. Navigate to `/mvc/friends` to view your friends.
2. Send, accept, or decline friendship invitations.

### Admin Dashboard

1. Access the admin dashboard at `/admin-dashboard/admin-dashboard`.
2. Manage users, posts, and comments.


## Authors

Aleksandra Frej https://github.com/alexef360, 

Mahmoud Najmeh  https://github.com/MN10101, 

Michal Frost    https://github.com/micfrost,

Maria Ibañez    https://github.com/MariaIRubio


# Demo
## Create an account
![Coders001.png](src/main/resources/screens/Coders001.png)

## Validation
![Coders018.png](src/main/resources/screens/Coders018.png)

## Login
![Coders002.png](src/main/resources/screens/Coders002.png)

## Feed
![Coders003.png](src/main/resources/screens/Coders003.png)

## Member Page
![Coders004.png](src/main/resources/screens/Coders004.png)
![Coders005.png](src/main/resources/screens/Coders005.png)

## Update Member
![Coders017.png](src/main/resources/screens/Coders017.png)

## Success notification
![Coders019.png](src/main/resources/screens/Coders019.png)


## Your Friends
![Coders006.png](src/main/resources/screens/Coders006.png)

## All Members 
![Coders007.png](src/main/resources/screens/Coders007.png)

## Friendship Invitations
![Coders008.png](src/main/resources/screens/Coders008.png)

## Your Posts
![Coders009.png](src/main/resources/screens/Coders009.png)

## Post Page
![Coders010.png](src/main/resources/screens/Coders010.png)

## Post Form
![Coders011.png](src/main/resources/screens/Coders011.png)

## Comment Form
![Coders012.png](src/main/resources/screens/Coders012.png)

## Admin Dashboard
![Coders013.png](src/main/resources/screens/Coders013.png)

## All Members
![Coders014.png](src/main/resources/screens/Coders014.png)

## All Posts
![Coders015.png](src/main/resources/screens/Coders015.png)

## All Comments
![Coders016.png](src/main/resources/screens/Coders016.png)
