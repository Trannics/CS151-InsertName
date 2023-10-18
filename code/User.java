import java.util.*;

// User class represents a user with a username and password
public class User {
    private String userName;
    private String password;
    private List<Post> posts;
    private List<Comment> comments;
    private Date createdDate;

    // Constructor to create a User with a username and password
    public User(String name, String password) {
        userName = name;
        this.password = password;
        posts = new ArrayList<>();
        comments = new ArrayList<>();
        createdDate = new Date();
    }

    // Get the username of the user
    public String getUsername() {
        return userName;
    }

    // Get the password of the user
    public String getPassword() {
        return password;
    }

    // Update the username of the user
    public void updateUserName(String newName) {
        userName = newName;
    }

    // Update the password of the user
    public void updatePassword(String newPassword) {
        password = newPassword;
    }

    // Delete the user and associated posts and comments
    public void deleteUser() {
        userName = null;
        password = null;
        posts.clear();
        comments.clear();
    }

    // Get a sorted list of posts created by the user
    public List<Post> getPosts() {
        posts.sort(Comparator.comparing(Post::getCreatedDate));
        return posts;
    }

    // Get a sorted list of comments created by the user
    public List<Comment> getComments() {
        comments.sort(Comparator.comparing(Comment::getCreatedDate));
        return comments;
    }

    // Add a post to the user's list of posts
    public void addPost(Post post) {
        posts.add(post);
    }

    // Add a comment to the user's list of comments
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    // Get the created date of the user
    public Date getCreatedDate() {
        return createdDate;
    }

    // Calculate and return the user's karma based on post and comment votes
    public int getKarma() {
        int karma = 0;
        for (Post post: posts) {
            karma += post.getKarma();
        }
        for (Comment comment: comments) {
            karma += comment.getKarma();
        }
        return karma;
    }
}
