/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ram com
 */
public class Register {
    
    public static void registerUser() {
        Connection connection = DB.connectDB();
        Scanner scanner = new Scanner(System.in);
        
        // Register
        System.out.print("Input your username: ");
        String username = scanner.nextLine();
        System.out.print("Input your email: ");
        String email = scanner.nextLine();
        System.out.print("Input your password: ");
        String password = scanner.nextLine();
        
        scanner.close();
        
        // Store User Credentials
        try {
            String checkUserQuery = "SELECT * FROM user WHERE email = ?";
            PreparedStatement checkUserStatement;
            checkUserStatement = connection.prepareStatement(checkUserQuery);
            
            checkUserStatement.setString(1, email);
            
            ResultSet resultSet = checkUserStatement.executeQuery();
            
            if(resultSet.next()) {
                // User Already Exist
                System.err.println("Email already exist!");
            } else {
                // Dispaly Credentials
                System.out.println("Your username is: " + username);
                System.out.println("Your email is: " + email);
                System.out.println("Your password is: " + password);
                
                String insertUserQuery = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
                PreparedStatement insertUserStatement;
                insertUserStatement = connection.prepareStatement(insertUserQuery);

                insertUserStatement.setString(1, username);
                insertUserStatement.setString(2, email);
                insertUserStatement.setString(3, password);

                insertUserStatement.executeUpdate();
                
                System.out.println("Rgistered Successfully");
            }
            
            // Close Resources
            checkUserStatement.close();
            connection.close();
        } catch (SQLException ex) {
//            Logger.getLogger(Pomodoro_auth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
