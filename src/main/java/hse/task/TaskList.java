package hse.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Stores tasks and provides operations that change or query the task collection. */
public class TaskList {
    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this(new ArrayList<>());
    }

    /** Creates a task list containing the supplied tasks. */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Adds a task to the end of the list. */
    public void add(Task task) {
        assert task != null : "A task list cannot contain null tasks.";
        tasks.add(task);
    }

    /** Returns the task at a zero-based index. */
    public Task get(int index) {
        assertValidIndex(index);
        return tasks.get(index);
    }

    /** Removes and returns the task at a zero-based index. */
    public Task delete(int index) {
        assertValidIndex(index);
        return tasks.remove(index);
    }

    /** Marks and returns the task at a zero-based index. */
    public Task mark(int index) {
        Task task = get(index);
        task.markAsDone();
        return task;
    }

    /** Unmarks and returns the task at a zero-based index. */
    public Task unmark(int index) {
        Task task = get(index);
        task.markAsNotDone();
        return task;
    }

    /** Returns the number of tasks in the list. */
    public int size() {
        return tasks.size();
    }

    /** Returns a copy of all tasks, preserving their display order. */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    /** Returns tasks whose descriptions contain the supplied keyword. */
    public List<Task> find(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /** Returns tasks that are deadlines or events occurring on a supplied date. */
    public List<Task> getTasksOn(LocalDate date) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Deadline && ((Deadline) task).isOn(date)) {
                matchingTasks.add(task);
            } else if (task instanceof Event && ((Event) task).occursOn(date)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /** Returns the one-based display positions of tasks that occur on a supplied date. */
    public List<Integer> getTaskPositionsOn(LocalDate date) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task instanceof Deadline && ((Deadline) task).isOn(date)) {
                positions.add(i + 1);
            } else if (task instanceof Event && ((Event) task).occursOn(date)) {
                positions.add(i + 1);
            }
        }
        return positions;
    }

    /** Asserts that an index refers to an existing task. */
    private void assertValidIndex(int index) {
        assert index >= 0 && index < tasks.size() : "Task index must identify an existing task.";
    }
}
