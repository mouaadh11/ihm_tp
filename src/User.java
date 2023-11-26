import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int user_id;
    private String username;
    private String email;
    private String password;

    // Constructor
    public User(int user_id, String username, String email, String password) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Method to authenticate user by checking username and password in the database
    public boolean login() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password")) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If there is a match, the user is authenticated
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception based on your application's requirements
        }
    }

    // Method to create a new user in the database
    public boolean signup() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password")) {
            String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0; // If rowsAffected > 0, user creation was successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle the exception based on your application's requirements
        }
    }

    // Method to log out (implementation depends on your specific requirements)
    public void logout() {
        // Perform logout actions, if any
    }

    // Method to change the user's password in the database
    public void changePassword(String newPassword) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password")) {
            String query = "UPDATE users SET password = ? WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setInt(2, user_id);
                preparedStatement.executeUpdate();
                this.password = newPassword; // Update the password in the object as well
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception based on your application's requirements
        }
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Main method for testing
//    public static void main(String[] args) {
//        // Example usage
//        User user = new User(1, "exampleUser", "user@example.com", "password123");
//
//        // Login
//        if (user.login()) {
//            System.out.println("Login successful!");
//        } else {
//            System.out.println("Login failed.");
//        }
//
//        // Change password
//        user.changePassword("newPassword456");
//
//        // Logout (placeholder, actual implementation depends on your requirements)
//        user.logout();
//
//        // Display username and email
//        System.out.println("Username: " + user.getUsername());
//        System.out.println("Email: " + user.getEmail());
//    }
}
