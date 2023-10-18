// Vote class represents upvotes and downvotes for a post or comment
public class Vote {
    private int upvotes;
    private int downvotes;

    // Constructor to initialize upvotes and downvotes to zero
    public Vote() {
        this.upvotes = 0;
        this.downvotes = 0;
    }

    // Get the number of upvotes
    public int getUpvotes() {
        return upvotes;
    }

    // Get the number of downvotes
    public int getDownvotes() {
        return downvotes;
    }

    // Increment the upvote count
    public void upvote() {
        upvotes++;
    }

    // Increment the downvote count
    public void downvote() {
        downvotes++;
    }

    // Calculate and return the karma (upvotes - downvotes) for a post or comment
    public int getKarma() {
        return upvotes - downvotes;
    }
}
