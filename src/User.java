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

    public User(int userId, String username, String email) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
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

}
