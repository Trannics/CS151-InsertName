import java.util.*;

// Vote class represents upvotes and downvotes for a post or comment
public class Vote {
    private int upvotes;
    private int downvotes;
    private ArrayList<User> upvotedUsers;
    private ArrayList<User> downvotedUsers;

    // Constructor to initialize upvotes and downvotes to zero
    public Vote() {
        this.upvotes = 0;
        this.downvotes = 0;
        upvotedUsers = new ArrayList<>();
        downvotedUsers = new ArrayList<>();
    }

    // Get the number of upvotes
    public int getUpvotes() {
        return upvotes;
    }

    // Get the number of downvotes
    public int getDownvotes() {
        return downvotes;
    }

    // Check if a user has upvoted
    public boolean hasUpvoted(User user) {
        return upvotedUsers.contains(user);
    }

    // Check if a user has downvoted
    public boolean hasDownvoted(User user) {
        return downvotedUsers.contains(user);
    }

    // Increment the upvote count
    public void upvote(User user) {
        if (!upvotedUsers.contains(user)) {
            upvotes++;
            upvotedUsers.add(user);
            if (downvotedUsers.contains(user)) {
                downvotes--;
                downvotedUsers.remove(user);
            }
        }
    }

    // Increment the downvote count
    public void downvote(User user) {
        if (!downvotedUsers.contains(user)) {
            downvotes++;
            downvotedUsers.add(user);
            if (upvotedUsers.contains(user)) {
                upvotes--;
                upvotedUsers.remove(user);
            }
        }
    }

    // Calculate and return the karma (upvotes - downvotes) for a post or comment
    public int getKarma() {
        return upvotes - downvotes;
    }
}
