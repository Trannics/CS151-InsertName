import java.util.*;

public class RedditClone {
    private Login loginSystem;
    private List<User> loggedInUsers;
    private List<Post> posts;

    public RedditClone() {
        loginSystem = new Login();
        loggedInUsers = new ArrayList<>();
        posts = new ArrayList<>();
    }

    // Authenticate a user and add them to the list of logged-in users
    public User login(String username, String password) {
        User user = loginSystem.authenticate(username, password);
        if (user != null) {
            loggedInUsers.add(user);
        }
        return user;
    }

    // Logout a user and remove them from the list of logged-in users
    public void logout(User user) {
        loggedInUsers.remove(user);
    }

    // Create a new user and add them to the system
    public User createUser(String username, String password) {
        User user = loginSystem.createUser(username, password);
        loggedInUsers.add(user);
        return user;
    }

    // Delete an existing user
    public void deleteUser(User user) {
        loginSystem.deleteUser(user.getUsername(), user.getPassword());
        loggedInUsers.remove(user);
    }

    // Create a new post by an authenticated user
    public Post createPost(User user, String title, String content) {
        if (loggedInUsers.contains(user)) {
            Post post = new Post(title, content, user);
            user.addPost(post);
            posts.add(post);
            return post;
        }
        return null;
    }

    // Create a new comment on a post or another comment
    public Comment createComment(User user, String text, Post parentPost, Comment parentComment) {
        if (loggedInUsers.contains(user)) {
            Comment comment;
            if (parentPost != null) {
                comment = new Comment(text, user, parentPost);
                parentPost.addComment(comment);
            } else if (parentComment != null) {
                comment = new Comment(text, user, parentComment);
                parentComment.addComment(comment);
            } else {
                return null;
            }
            user.addComment(comment);
            return comment;
        }
        return null;
    }

    // Upvote a post
    public void upvotePost(User user, Post post) {
        if (loggedInUsers.contains(user)) {
            post.upvote();
        }
    }

    // Downvote a post
    public void downvotePost(User user, Post post) {
        if (loggedInUsers.contains(user)) {
            post.downvote();
        }
    }

    // Upvote a comment
    public void upvoteComment(User user, Comment comment) {
        if (loggedInUsers.contains(user)) {
            comment.upvote();
        }
    }

    // Downvote a comment
    public void downvoteComment(User user, Comment comment) {
        if (loggedInUsers.contains(user)) {
            comment.downvote();
        }
    }

    // Get the content of a post by title
    public String getPostContent(String title) {
        for (Post post : posts) {
            if (post.getTitle().equals(title)) {
                return post.getContent();
            }
        }
        return null; // Post not found
    }

    // Get all comments of an associated post
    public List<Comment> getPostComments(String title) {
        List<Comment> allComments = new ArrayList<>();
        for (Post post : posts) {
            if (post.getTitle().equals(title)) {
                allComments.addAll(post.getComments());
            }
        }
        return allComments;
    }

    // Get all registered users
    public List<User> getRegisteredUsers() {
        return loginSystem.getRegisteredUsers();
    }
}
