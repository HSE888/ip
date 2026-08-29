package hse.task;

/** Represents a task with a description and completion status. */
public class Task {
    private String description;
    private boolean isDone;

    /** Creates an incomplete task with the supplied description. */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Returns this task's description. */
    public String getDescription() {
        return description;
    }

    /** Marks this task as complete. */
    public void markAsDone() {
        isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markAsNotDone() {
        isDone = false;
    }

    /** Returns this task in the standard completion-status display format. */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }
}
