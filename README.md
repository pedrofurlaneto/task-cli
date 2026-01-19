# Task Tracker CLI
A simple, dependency-free Command Line Interface (CLI) designed to help you track and manage your daily tasks. This project focuses on native file system interaction, handling positional arguments, and JSON data management.

This project is part of the [roadmap.sh](https://roadmap.sh/projects/task-tracker) project challenges.

## 💻 Usage
The application uses positional arguments to handle commands. Replace task-cli with your actual execution command.

Adding Tasks
```bash
task-cli add "Buy groceries"
```

**Updating & Deleting**
```bash
# Update a task description
task-cli update 1 "Buy groceries and cook dinner"

# Delete a task
task-cli delete 1
```

**Changing Task Status**
```bash
# Mark as todo
task-cli mark-todo 1

# Mark as in-progress
task-cli mark-in-progress 1

# Mark as done
task-cli mark-done 1
```

**Listing Tasks**
```bash
# List all tasks
task-cli list

# List by status
task-cli list todo
task-cli list in-progress
task-cli list done
```
---
Project Link: https://roadmap.sh/projects/task-tracker