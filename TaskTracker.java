import java.io.*;
import java.util.*;
import java.nio.file.*;

public class TaskTracker {
    private static final String TASKS_FILE = "C:\\Users\\tjs83\\IdeaProjects\\untitled\\src\\tasks.json";
    private static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        loadTasks();

        String command = args[0];

        if (args.length < 1) {
            System.out.println("No tasks file specified");
        }



        switch (command) {
            case "add":
                if (args.length != 4) {
                    System.out.println("Usage: add <id> <description> <status>");
                    return;
                }
                addTask(args[1], args[2], args[3]);
                break;
            case "update":
                if (args.length != 4) {
                    System.out.println("Usage: update <id> <description> <status>");
                }
                updateTask(args[1], args[2], args[3]);
                break;
            case "delete":
                if (args.length != 4) {
                    System.out.println("Usage: delete <id>");
                    return;
                }
                deleteTask(args[1]);
                break;
            case "list":
                if (args.length == 1) {
                    listTasksByStatus(args[1]);
                } else {
                    System.out.println("Usage: list [status]");
                }
                break;
            default:
                System.out.println("unknown command: " + command);
                break;
        }
    }

    private static void loadTasks() {
        File file = new File("C:\\Users\\tjs83\\IdeaProjects\\untitled\\src\\tasks.json");


        if (!file.exists()) {
            System.out.println("No tasks file found, starting anew");
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

                Task task = new Task(id, description, status);
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
                    writer.write(",");
                }
            }
            writer.println("]");
        } catch (IOException e) {
            System.out.println("Error writing tasks.json" + e.getMessage());
        }
    }

    private static void addTask(String id, String description, String status) {
        Task task = new Task(id, description, status);
        tasks.add(task);
        saveTasks();
    }

    private static void updateTask(String id, String newDescription, String newStatus) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setDescription(newDescription);
                task.setStatus(newStatus);
                saveTasks();
                return;

            }
        }
        System.out.println("Task not found with id " + id);
    }

    private static void deleteTask(String id) {
        tasks.removeIf(task -> task.getId().equals(id));
        saveTasks();
    }

    private static void listAllTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private static void listTasksByStatus(String status) {
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase(status)) {
                System.out.println(task);
            }
        }
    }

}


