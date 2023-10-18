import java.util.*;

// Post class represents a user's post with title, content, and comments
public class Post {
    private String title;
    private String content;
    private User author;
    private Date createdDate;
    private List<Comment> comments;
    private Vote vote;

    // Constructor to create a new post with title, content, and an author
    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdDate = new Date();
        this.comments = new ArrayList<>();
        this.vote = new Vote();
    }

    // Delete the post
    public void deletePost() {
        title = null;
        content = null;
        author = null;
        createdDate = null;
        comments = null;
        vote = null;
    }

    // Get the title of the post
    public String getTitle() {
        return title;
    }

    // Get the content of the post
    public String getContent() {
        return content;
    }

    // Get the author of the post
    public User getAuthor() {
        return author;
    }

    // Get the created date of the post
    public Date getCreatedDate() {
        return createdDate;
    }

    // Get all comments associated with the post
    public List<Comment> getComments() {
        return comments;
    }

    // Update the title of the post with a new title
    public void updateTitle(String newTitle) {
        title = newTitle;
    }

    // Update the content of the post with new content
    public void updateContent(String newContent) {
        content = newContent;
    }

    // Add a comment to be associated with the post
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    // Upvote the post
    public void upvote() {
        vote.upvote();
    }

    // Downvote the post
    public void downvote() {
        vote.downvote();
    }

    // Get the karma of the post
    public int getKarma() {
        return vote.getKarma();
    }
}
