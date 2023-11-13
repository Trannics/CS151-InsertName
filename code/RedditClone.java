import java.util.*;

public class RedditClone {
    private Login loginSystem;
    private User loggedInUser;
    private List<Post> posts;

    public RedditClone() {
        loginSystem = new Login();
        loggedInUser = null;
        posts = new ArrayList<>();
    }

    // Create a new user and add them to the system
    public User createUser(String username, String password) {
        User user = loginSystem.createUser(username, password);
        loggedInUser = user;
        return user;
    }

    // Authenticate a user and add them to the list of logged-in users
    public User login(String username, String password) {
        User user = loginSystem.authenticate(username, password);
        if (user != null) {
            loggedInUser = user;
        }
        return user;
    }

    // Logout a user and remove them from the list of logged-in users
    public void logout() {
        loggedInUser = null;
    }

    // Delete an existing user
    public void deleteUser() {
        if (loggedInUser != null) {
            loginSystem.deleteUser(loggedInUser.getUsername(), loggedInUser.getPassword());
            loggedInUser.deleteUser();
            loggedInUser = null;
        }
    }

    // Create a new post by an authenticated user
    public Post createPost(String title, String content) {
        if (loggedInUser != null) {
            Post post = new Post(title, content, loggedInUser);
            loggedInUser.addPost(post);
            posts.add(post);
            return post;
        }
        return null;
    }

    // Create a new comment on a post or another comment
    public Comment createComment(User user, String text, Post parentPost, Comment parentComment) {
        if (loggedInUser != null) {
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

    // Get the currently logged-in user if they are already logged in
    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Update a post's title
    public void updatePostTitle(Post post, String title) {
        if (post.getAuthor().equals(loggedInUser)) {
            post.updateTitle(title);
        }
    }

    // Update a post's content
    public void updatePostContent(Post post, String content) {
        if (post.getAuthor().equals(loggedInUser)) {
            post.updateContent(content);
        }
    }

    // Update a comment
    public void updateComment(Comment comment, String content) {
        if (comment.getAuthor().equals(loggedInUser)) {
            comment.updateText(content);
        }
    }

    // Delete a post
    public void deletePost(Post post) {
        if (post.getAuthor().equals(loggedInUser)) {
            post.deletePost();
        }
    }

    // Delete a comment
    public void deleteComment(Comment comment) {
        if (comment.getAuthor().equals(loggedInUser)) {
            comment.deleteComment();
        }
    }

    // Upvote a post
    public void upvotePost(Post post) {
        if (loggedInUser != null) {
            post.upvote(loggedInUser);
        }
    }

    // Downvote a post
    public void downvotePost(Post post) {
        if (loggedInUser != null) {
            post.downvote(loggedInUser);
        }
    }

    // Upvote a comment
    public void upvoteComment(Comment comment) {
        if (loggedInUser != null) {
            comment.upvote(loggedInUser);
        }
    }

    // Downvote a comment
    public void downvoteComment(Comment comment) {
        if (loggedInUser != null) {
            comment.downvote(loggedInUser);
        }
    }

    // Get the content of a post by title
    public String getPostContent(String title) {
        for (Post post: posts) {
            if (post.getTitle().equals(title)) {
                return post.getContent();
            }
        }
        return null;
    }

    // Get all comments of an associated post
    public List<Comment> getPostComments(String title) {
        List<Comment> allComments = new ArrayList<>();
        for (Post post: posts) {
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

    // Get a sorted list of posts based on date
    public List<Post> getPostsByDate() {
        posts.sort(Comparator.comparing(Post::getCreatedDate));
        return posts;
    }

    // Get a sorted list of posts based on karma
    public List<Post> getPostsByKarma() {
        posts.sort(Comparator.comparing(Post::getKarma).reversed());
        return posts;
    }

    // Get a sorted list of comments based on date
    public List<Comment> getCommentsByDate() {
        List<Comment> allComments = new ArrayList<>();
        for (Post post: posts) {
            allComments.addAll(post.getComments());
        }
        allComments.sort(Comparator.comparing(Comment::getCreatedDate));
        return allComments;
    }

    // Get a sorted list of comments based on karma
    public List<Comment> getCommentsByKarma() {
        List<Comment> allComments = new ArrayList<>();
        for (Post post: posts) {
            allComments.addAll(post.getComments());
        }
        allComments.sort(Comparator.comparing(Comment::getKarma).reversed());
        return allComments;
    }
}
