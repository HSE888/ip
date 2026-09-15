# Test Plan

## Automated Tests

The JUnit suite covers command parsing, date parsing, task completion state, date matching,
task-list mutations and searches, chronological sorting, and storage loading and saving.

Run the suite and code-style checks with `./gradlew clean check`.

## Sorting Tasks

1. Add a deadline dated `2026-09-20`, an event starting `2026-09-15 1400`, and a todo.
2. Enter `sort`.
3. Enter `list`.
4. Verify that the event appears before the deadline and the todo appears last.
5. Restart HSE and enter `list` again to verify that the sorted order was saved.
6. Enter `sort extra` and verify that HSE reports an invalid command.
