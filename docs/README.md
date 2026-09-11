# HSE User Guide

HSE is a task manager that supports todos, deadlines, events, searching, and chronological sorting.

## Commands

| Command | Example | Result |
| --- | --- | --- |
| Add a todo | `todo read book` | Adds an undated task. |
| Add a deadline | `deadline submit report /by 2026-09-20` | Adds a task due at the specified date or time. |
| Add an event | `event meeting /from 2026-09-15 1400 /to 2026-09-15 1500` | Adds a task with a start and end time. |
| List tasks | `list` | Displays every task in its stored order. |
| Sort tasks | `sort` | Sorts dated tasks chronologically and places todos after them. |
| Find tasks | `find book` | Displays tasks whose descriptions contain the keyword. |
| Find tasks on a date | `finddate 2026-09-15` | Displays deadlines and events occurring on that date. |
| Mark a task | `mark 1` | Marks task 1 as complete. |
| Unmark a task | `unmark 1` | Marks task 1 as incomplete. |
| Delete a task | `delete 1` | Removes task 1. |
| Exit | `bye` | Closes HSE. |

## Sorting Tasks

Use `sort` without extra arguments to reorder the saved task list. Deadlines are ordered by due date and
events by start date. Todos remain after dated tasks, while tasks with the same sort time keep their
existing relative order.

Example:

```text
deadline submit report /by 2026-09-20
event project meeting /from 2026-09-15 1400 /to 2026-09-15 1500
todo read notes
sort
```

HSE responds:

```text
Tasks sorted by date.
```
