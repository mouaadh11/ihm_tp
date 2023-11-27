import java.util.List;

public class TaskGroup {
    private int groupId;
    private String groupName;
    private List<Task> tasks;

    // Constructor
    public TaskGroup(int groupId, String groupName, List<Task> tasks) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.tasks = tasks;
    }

    // Getter and Setter methods for each attribute

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // Add a task to the group
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Remove a task from the group
    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
