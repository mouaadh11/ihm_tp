/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ram com
 */
public class Login {
    public static void loginUser() {
        Connection connection = DB.connectDB();
        Scanner scanner = new Scanner(System.in);
        
        // Login
        System.out.print("Input your email: ");
        String email = scanner.nextLine();
        System.out.print("Input your password: ");
        String password = scanner.nextLine();
        
        scanner.close();
        
        // Check User Credentials
        try {
            String checkUserQuery = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement checkUserStatement;
            checkUserStatement = connection.prepareStatement(checkUserQuery);
            
            checkUserStatement.setString(1, email);
            checkUserStatement.setString(2, password);
            
            ResultSet resultSet = checkUserStatement.executeQuery();
            
            if(!resultSet.next()) {
                // User Does Not Exist
                System.err.println("Email does not exist!");
                // redirect to signUp page
                // TODO: 13/12/2023 redirect to signUp page
            } else {
                // Dispaly Credentials
                System.out.println("Your email is: " + email);
                System.out.println("Your password is: " + password);
                //open a session
                System.out.println("Logged In Successfully");

                // TODO: 13/12/2023 creating a user obj and bring data to it
                User user = DB.getUser(email, password);
                List<Task> tasks = DB.getTasks();
                for (Task task : tasks) {
                    task.setInFocusTime(DB.getSettings().getSessionDuration());
                    task.setLongBreakTime(DB.getSettings().getLongBreak());
                    task.setShortBreakTime(DB.getSettings().getShortBreak());
                    task.setInFocusTimes(DB.getSettings().getShortBreakAfter());
                    task.setAutoStartBreak(DB.getSettings().isAutoStartBreak());
                    task.setDisableBreaks(DB.getSettings().isDisableBreaks());
                    task.setAutoStartPomodoro(DB.getSettings().isAutoStartPomodoro());

                }
                // TODO: 14/12/2023 auto start pomodoro

                // TODO: 13/12/2023 redirect to the main page
                //UI
            }
            
            // Close Resources
            checkUserStatement.close();
            connection.close();
        } catch (SQLException ex) {
//            Logger.getLogger(Pomodoro_auth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
