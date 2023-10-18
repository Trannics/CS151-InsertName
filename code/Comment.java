import java.util.*;

// Comment class represents a user's comment on a post or another comment
public class Comment {
    private String text;
    private User author;
    private Date createdDate;
    private Comment parentComment;
    private List<Comment> subComments;
    private Post parentPost;
    private Vote vote;

    // Constructor to create a comment on a post with text and an author
    public Comment(String text, User author, Post parentPost) {
        this.text = text;
        this.author = author;
        this.parentPost = parentPost;
        this.subComments = new ArrayList<>();
        this.createdDate = new Date();
        this.vote = new Vote();
    }

    // Constructor to create a comment on another comment with text and an author
    public Comment(String text, User author, Comment parentComment) {
        this.text = text;
        this.author = author;
        this.parentComment = parentComment;
        this.subComments = new ArrayList<>();
        this.createdDate = new Date();
        this.vote = new Vote();
    }

    // Delete the comment
    public void deleteComment() {
        text = null;
        author = null;
        createdDate = null;
        parentComment = null;
        subComments = null;
        parentPost = null;
        vote = null;
    }

    // Add subcomment to parent comment
    public void addComment(Comment comment) {
        subComments.add(comment);
    }

    // Get the text of the comment
    public String getText() {
        return text;
    }

    // Get the author of the comment
    public User getAuthor() {
        return author;
    }

    // Get the created date of the comment
    public Date getCreatedDate() {
        return createdDate;
    }

    // Get the parent comment of a particular comment
    public Comment getParentComment() {
        return parentComment;
    }

    // Get the parent post of the comment
    public Post getParentPost() {
        return parentPost;
    }

    // Update the text of the comment
    public void updateText(String newText) {
        text = newText;
    }

    // Update the parent comment of a particular post
    public void updateParentComment(Comment newParentComment) {
        parentComment = newParentComment;
    }

    // Update the parent post of the comment
    public void updateParentPost(Post newParentPost) {
        parentPost = newParentPost;
    }

    // Upvote the comment
    public void upvote() {
        vote.upvote();
    }

    // Downvote the comment
    public void downvote() {
        vote.downvote();
    }

    // Get the karma of the comment
    public int getKarma() {
        return vote.getKarma();
    }
}
