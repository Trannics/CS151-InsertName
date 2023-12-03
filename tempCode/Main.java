import javafx.application.Application;


public class Main {
    public static void main(String[] args) {

        RedditClone redditClone = new RedditClone();
        redditClone.createUser("testUser", "password");
        redditClone.login("testUser", "password");
        Post post = redditClone.createPost("Test Post", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");


        redditClone.logout();
        redditClone.createUser("very_different_UserXX", "password");
        redditClone.login("very_different_UserXX", "password");
        Post post2 = redditClone.createPost("Indulgence Confinde", "May indulgence difficulty ham can put especially. Bringing remember for supplied her why was confined. Middleton principle did she procuring extensive believing add. ");

        Post post3 = redditClone.createPost("Elderly rest", "Or kind rest bred with am shed then. In raptures building an bringing be. Elderly is detract tedious assured private so to visited. Do travelling companions contrasted it. Mistress strongly remember up to. Ham him compass you proceed calling detract. Better of always missed we person mr. September smallness northward situation few her certainty something.");
        Post post4 = redditClone.createPost("My Friend no full", "Full he none no side. Uncommonly surrounded considered for him are its. It we is read good soon. My to considered delightful invitation announcing of no decisively boisterous. Did add dashwoods deficient man concluded additions resources.");


        Application.launch(JavaFX.class);
    }
}
