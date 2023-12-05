# Project Structure
This is an overview of the project structure and a breakdown of how various classes interact with each other. The code written includes five main classes: '**Login**', '**Vote**', '**Post**', '**User**', and '**Comment**'. These classes interact to create a simple system for user authentication, voting, and content creation, including posts and comments.

## Changelog
* '**Vote**' class now only allows the user to either upvote or downvote a post/comment once. A user can still change their vote even after they already voted.
* '**Post**' class now allows the program to return a sorted list of comments associated with a post based on either date created or karma
* '**User**' class now allows the program to return a sorted list of posts and comments created by a user based on either data created or karma
* '**Comment**' class now allows the program to return a sorted list of sub comments of a comment based on either date created or karma
* Code logic fixes (Bug fixes)
* JUnit Test Cases are now available in '**RedditCloneTest**'

## Class Responsibilities
Look for '**NEW**' to find newly created or updated methods for the most recent commit.

### 'Login'
* This class is responsible for managing user authentication and user creation.
* It stores a list of '**User**' objects.
* '**authenticate**' method checks if a given username and password match any existing user's credentials.
* '**createUser**' method allows creating a new user.
* ‘**deleteUser**’ method removes an existing user from the system.
* '**getUserByDate**' method returns a list of registered users sorted by their creation date
*  **NEW** - '**getUserByKarma**' method returns a list of registered users sorted by their karma

### 'Vote'
* This class handles the voting mechanism for posts and comments.
* It tracks the number of upvotes and downvotes for a particular post or comment.
* '**getUpvotes**' and '**getDownvotes**' methods return the respective counts.
* **NEW** - '**hasUpvoted**' and '**hasDownvoted**' methods checks whether the user has either upvoted or downvoted before.
* **NEW** - '**upvote**' and '**downvote**' methods increment the upvotes and downvotes depending on if they have done either or before.
* '**getKarma**' returns the difference between upvotes and downvotes for a post or comment, which represents its overall karma.

### 'Post'
* Represents a post that can be created by a user.
* Stores post-related information such as the title, content, author, creation date, comments, and vote counts.
* Users can upvote and downvote posts.
* '**addComment**' method allows adding comments to the post.
* '**getKarma**' calculates and returns the post's karma based on its upvotes and downvotes. * ‘**deletePost**’ method removes a post from the system
* **NEW** - '**getCommentsByDate()**' and '**getCommentsByKarma**' methods get a sorted list of comments associated with a post based on either date or karma

### 'User'
* Represents a user with a username, password, and a list of posts and comments.
* Users can update their username or delete their account.
* ‘**deleteUser**’ method deletes a user account and removes associated posts and comments.
* '**getPosts**' and '**getComments**' methods return the user's posts and comments sorted by their creation date.
* '**addPost**' and '**addComment**' methods add posts and comments created by a particular user.
* '**getKarma**' calculates and returns the user's karma based on the karma of their posts and comments.
* **NEW** - '**getPostsByDate**' and '**getCommentsByDate**' methods get a sorted list of posts and comments created by the user based on date
* **NEW** - '**getPostsByKarma**' and '**getCommentsByKarma**' methods get a sorted list of posts and comments created by the user based on karma

### 'Comment'
* Represents a comment made by a user on a post or another comment.
* Stores the comment text, author, creation date, and vote counts.
* Users can upvote and downvote comments.
* '**getParentComment**' and '**getParentPost**' methods return the parent comment or post.
* '**getKarma**' calculates and returns the comment's karma based on its upvotes and downvotes.
* ‘**deleteComment**’ method removes a comment from the system
* **NEW** - '**getSubCommentsByDate**' and '**getSubCommentsByKarma**' methods get a sorted list of sub comments based on either date or karma

## Overall Driving Class
'**RedditClone**' is the overall driving class that manages the core functionalities, including user authentication, content creation, and voting. This class provides methods for user login, logout, and creating, deleting, and interacting with posts and comments.

## Basic Interactions
1. User Authentication:
  * Users are created and authenticated using the '**Login**' class. New users are created via the '**createUser**' method, and existing users are authenticated using the '**authenticate**' method.
2. Post and Comment Creation:
  * Users create posts and comments using the '**Post**' and '**Comment**' classes. They can use the '**addPost**' and '**addComment**' methods in the '**User**' class to create these entities.
3. Voting:
  * Users can '**upvote**' and '**downvote**' posts and comments using the upvote and downvote methods in the '**Post**' and '**Comment**' classes.
4. User Information and Karma:
  * Users can retrieve information about their posts and comments using the '**getPosts**' and '**getComments**' methods in the '**User**' class. The '**getKarma**' method calculates and returns the overall karma of a user based on their posts and comments.
5. Comment Hierarchy:
  * The '**Comment**' class allows for comments to be threaded by linking child comments to their parent comments, creating a hierarchical structure.
