import java.io.*;
import java.util.*;
import java.nio.file.*;

public class TaskTracker {
    private static final String TASKS_FILE = "tasks.json";
    private static List<Task> tasks = new ArrayList<>();

    private static void loadTasks() {
        File file = new File("tasks.txt");


        if (!file.exists()) {
            System.out.println("No tasks found, starting anew");
            return;
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(TASKS_FILE)));
            content = content.trim();

            if (content.isEmpty() || content.equals("[]")) {
                return;
            }
            content = content.substring(1, content.length() - 1);

            String[] taskStrings = content.split("\\},\\{");

            for (String taskString : taskStrings) {
                taskString = taskString.replace("}", "").replace("}", "").replace("\"", "");
                String[] attributes = taskString.split(",");

                String id = attributes[0].split(":")[1];
                String description = attributes[1].split(":")[1];
                String status = attributes[2].split(":")[1];
                String createdAt = attributes[3].split(":")[1];
                String updatedAt = attributes[4].split(":")[1];

                Task task = new Task(id, description, status, createdAt, updatedAt);
                tasks.add(task);

            }
        } catch (IOException e) {
            System.out.println("Error reading tasks.json" + e.getMessage());
        }
    }

    private static void saveTasks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TASKS_FILE))) {
            writer.println("[");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                String taskJson = String.format("{\"id\":\"%s\",\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                        task.getId(), task.getDescription(), task.getStatus(), task.getCreatedAt(), task.getUpdatedAt()
                );
                writer.println(taskJson);
                if (i < tasks.size() - 1) {
                    writer.println(",");
                }
            }
            writer.println("]");
        } catch (IOException e) {
            System.out.println("Error writing tasks.json" + e.getMessage());
        }
    }

    private static void addTask(int id, String description, String status) {
        Task task = new Task(id, description, status, gete)
    }
}

