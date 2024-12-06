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

### Development URLs

* **H2 Database Console:** [http://localhost:8083/h2-console](http://localhost:8083/h2-console)
* **Actuator Mappings:** [http://localhost:8083/actuator/mappings](http://localhost:8083/actuator/mappings)

## HomeWork 3
Add the starter to your Spring Boot project:
* **logger-starter:** [logger-starter](https://github.com/kamserg1608/logger-starter)
```
<dependency>
    <groupId>org.example.t1.http.logger</groupId>
    <artifactId>logger-starter</artifactId>
    <version>${org.example.t1.http.logger}</version>
</dependency>
```

