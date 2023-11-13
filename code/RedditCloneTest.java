import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

class RedditCloneTest {

    @Test
    void testCreateUser() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void testLogin() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        User loggedInUser = redditClone.login("testUser", "password");
        assertNotNull(loggedInUser);
        assertEquals(user, loggedInUser);
    }

    @Test
    void testCreatePost() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        assertNotNull(post);
        assertEquals("Test Post", post.getTitle());
    }

    @Test
    void testCreateComment() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        Comment comment = redditClone.createComment(user, "Test Comment", post, null);
        assertNotNull(comment);
        assertEquals("Test Comment", comment.getText());
    }

    @Test
    void testUpvotePost() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        redditClone.upvotePost(post);
        assertEquals(1, post.getKarma());
    }

    @Test
    void testDownvotePost() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        redditClone.downvotePost(post);
        assertEquals(-1, post.getKarma());
    }

    @Test
    void testUpvoteComment() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        Comment comment = redditClone.createComment(user, "Test Comment", post, null);
        redditClone.upvoteComment(comment);
        assertEquals(1, comment.getKarma());
    }

    @Test
    void testDownvoteComment() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        Comment comment = redditClone.createComment(user, "Test Comment", post, null);
        redditClone.downvoteComment(comment);
        assertEquals(-1, comment.getKarma());
    }

    @Test
    void testGetPostContent() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        String content = redditClone.getPostContent("Test Post");
        assertEquals("This is a test post.", content);
    }

    @Test
    void testGetPostComments() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        Comment comment = redditClone.createComment(user, "Test Comment", post, null);
        List<Comment> comments = redditClone.getPostComments("Test Post");
        assertEquals(1, comments.size());
        assertEquals("Test Comment", comments.get(0).getText());
    }

    @Test
    void testGetRegisteredUsers() {
        RedditClone redditClone = new RedditClone();
        User user1 = redditClone.createUser("user1", "password1");
        User user2 = redditClone.createUser("user2", "password2");
        List<User> registeredUsers = redditClone.getRegisteredUsers();
        assertEquals(2, registeredUsers.size());
        assertEquals("user1", registeredUsers.get(0).getUsername());
        assertEquals("user2", registeredUsers.get(1).getUsername());
    }

    @Test
    void testDeleteUser() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        redditClone.createPost("Test Post", "This is a test post.");
        redditClone.deleteUser();
        assertNull(redditClone.getLoggedInUser());
        assertEquals(0, redditClone.getRegisteredUsers().size());
        assertEquals(0, user.getPosts().size());
    }

    @Test
    void testUpdatePost() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        redditClone.updatePostTitle(post, "Updated Title");
        redditClone.updatePostContent(post, "Updated Content");
        assertEquals("Updated Title", post.getTitle());
        assertEquals("Updated Content", post.getContent());
    }

    @Test
    void testUpdateComment() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        Comment comment = redditClone.createComment(user, "Test Comment", post, null);
        redditClone.updateComment(comment, "Updated Comment");
        assertEquals("Updated Comment", comment.getText());
    }

    @Test
    void testDeletePost() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        redditClone.deletePost(post);
        assertEquals("This post has been deleted by the user.", post.getTitle());
        assertNull(post.getContent());
        assertNull(post.getAuthor());
        assertNull(post.getCreatedDate());
        assertEquals(0, post.getKarma());
        assertEquals(0, post.getComments().size());
    }

    @Test
    void testDeleteComment() {
        RedditClone redditClone = new RedditClone();
        User user = redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "This is a test post.");
        Comment comment = redditClone.createComment(user, "Test Comment", post, null);
        redditClone.deleteComment(comment);
        assertEquals("This comment has been deleted by the user.", comment.getText());
        assertNull(comment.getAuthor());
        assertNull(comment.getCreatedDate());
        assertNull(comment.getParentComment());
        assertNull(comment.getParentPost());
        assertEquals(0, comment.getKarma());
        assertEquals(0, comment.getSubCommentsByDate().size());
    }
}
