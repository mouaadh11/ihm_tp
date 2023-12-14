/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.List;

/**
 *
 * @author ram com
 */
public class Data {
    private User user;
    private List<Task> tasks;
    private Settings settings;
    
    public Data(User user, List<Task> tasks, Settings settings) {
        this.user = user;
        this.tasks = tasks;
        this.settings = settings;
    }
    
}
