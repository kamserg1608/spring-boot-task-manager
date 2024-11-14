# Open School T1 Stream 13

## HomeWork 1

### API Endpoints

| Method | Endpoint    | Description                    |
|--------|-------------|--------------------------------|
| POST   | /tasks      | Creates a new task.            |
| GET    | /tasks/{id} | Retrieves a task by its ID.    |
| PUT    | /tasks/{id} | Updates an existing task.      |
| DELETE | /tasks/{id} | Deletes a task.                |
| GET    | /tasks      | Retrieves a list of all tasks. |

### Task Entity

The `Task` entity represents a task with the following attributes:

| Attribute     | Description                                      |
|---------------|--------------------------------------------------|
| `id`          | Unique identifier for the task.                  |
| `title`       | Short description of the task.                   |
| `description` | Detailed description of the task.                |
| `userId`      | Identifier of the user associated with the task. |

### Aspect Logging

The application uses AOP to log various events during task operations. The following advice types are implemented:

| Advice Type    | Description                                                  |
|----------------|--------------------------------------------------------------|
| Before         | Logs before a method is executed.                            |
| AfterThrowing  | Logs when a method throws an exception.                      |
| AfterReturning | Logs when a method returns successfully.                     |
| Around         | Logs both before and after a method, providing more control. |

## HomeWork 2