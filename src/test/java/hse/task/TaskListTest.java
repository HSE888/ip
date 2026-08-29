package hse.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class TaskListTest {
    @Test
    void find_matchingKeyword_returnsTasksInOriginalOrder() {
        Task firstMatch = new ToDos("read book");
        Task nonMatch = new ToDos("write notes");
        Task secondMatch = new Deadline("return book", LocalDate.of(2026, 9, 3).atStartOfDay());
        TaskList tasks = new TaskList(List.of(firstMatch, nonMatch, secondMatch));

        assertEquals(List.of(firstMatch, secondMatch), tasks.find("book"));
    }

    @Test
    void find_noMatchingKeyword_returnsEmptyList() {
        TaskList tasks = new TaskList(List.of(new ToDos("read notes")));

        assertTrue(tasks.find("book").isEmpty());
    }

    @Test
    void getTasksOn_matchingDate_returnsDeadlinesAndEventsButNotTodos() {
        LocalDate targetDate = LocalDate.of(2026, 9, 3);
        Task deadline = new Deadline("submit assignment", targetDate.atStartOfDay());
        Task event = new Event("camp", targetDate.minusDays(1).atTime(18, 0),
                targetDate.plusDays(1).atTime(10, 0));
        Task otherDeadline = new Deadline("later task", targetDate.plusDays(1).atStartOfDay());
        Task todo = new ToDos("read notes");
        TaskList tasks = new TaskList(List.of(deadline, event, otherDeadline, todo));

        assertEquals(List.of(deadline, event), tasks.getTasksOn(targetDate));
        assertEquals(List.of(1, 2), tasks.getTaskPositionsOn(targetDate));
    }

    @Test
    void getTasksOn_noMatchingTasks_returnsEmptyList() {
        LocalDate targetDate = LocalDate.of(2026, 9, 3);
        TaskList tasks = new TaskList(List.of(
                new Deadline("later task", targetDate.plusDays(1).atStartOfDay()),
                new ToDos("read notes")));

        assertTrue(tasks.getTasksOn(targetDate).isEmpty());
        assertTrue(tasks.getTaskPositionsOn(targetDate).isEmpty());
    }
}
