import java.util.*;

// Login class represents user authentication and user creation
public class Login {
    private List<User> users;

    // Constructor to create a new Login instance with an empty list of users
    public Login() {
        users = new ArrayList<User>();
    }

    // Authenticate a user with a given username and password. Returns the authenticated user or null if not found
    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Create a new user with the given username and password and adds the user to the list of users and returns the created user
    public User createUser(String username, String password) {
        User user = new User(username, password);
        users.add(user);
        return user;
    }

    // Delete an existing user with the given username and password and removes the user from the list of users
    public void deleteUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                users.remove(user);
            }
        }
    }

    // Get a date sorted list of registered users
    public List<User> getRegisteredUsers() {
        users.sort(Comparator.comparing(User::getCreatedDate));
        return users;
    }
}
