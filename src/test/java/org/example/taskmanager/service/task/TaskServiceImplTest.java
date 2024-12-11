package org.example.taskmanager.service.task;

import org.example.taskmanager.dto.TaskDto;
import org.example.taskmanager.entity.Task;
import org.example.taskmanager.exception.ResourceNotFoundException;
import org.example.taskmanager.exception.TaskAlreadyExistsException;
import org.example.taskmanager.mappers.TaskMapper;
import org.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Nested
    class WhenCreateTask {
        @Test
        void success() {
            TaskDto taskDto = new TaskDto(1L, "Test Task", "This is a test task", 1L);
            Task task = new Task(1L, "Test Task", "This is a test task", 1L);

            when(taskRepository.findById(taskDto.id())).thenReturn(Optional.empty());
            when(taskMapper.taskDtoToTask(taskDto)).thenReturn(task);
            when(taskRepository.save(task)).thenReturn(task);
            when(taskMapper.taskToTaskDto(task)).thenReturn(taskDto);

            TaskDto result = taskService.createTask(taskDto);

            assertNotNull(result);
            assertEquals(taskDto.id(), result.id());
            assertEquals(taskDto.title(), result.title());
            assertEquals(taskDto.description(), result.description());
            assertEquals(taskDto.userId(), result.userId());

            verify(taskRepository, times(1)).findById(taskDto.id());
            verify(taskMapper, times(1)).taskDtoToTask(taskDto);
            verify(taskRepository, times(1)).save(task);
            verify(taskMapper, times(1)).taskToTaskDto(task);
        }

        @Test
        void alreadyExists() {
            TaskDto taskDto = new TaskDto(1L, "Test Task", "This is a test task", 1L);

            when(taskRepository.findById(taskDto.id())).thenReturn(Optional.of(new Task(1L, "Test Task", "This is a test task", 1L)));

            assertThrows(TaskAlreadyExistsException.class, () -> taskService.createTask(taskDto));

            verify(taskRepository, times(1)).findById(taskDto.id());
            verify(taskMapper, never()).taskDtoToTask(taskDto);
            verify(taskRepository, never()).save(any(Task.class));
            verify(taskMapper, never()).taskToTaskDto(any(Task.class));
        }
    }


    @Test
    void testGetTaskById() {
        TaskDto taskDto = new TaskDto(1L, "Title", "Description", 1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task(1L, "Title", "Description", 1L)));
        when(taskMapper.taskToTaskDto(any(Task.class))).thenReturn(taskDto);

        TaskDto result = taskService.getTaskById(1L);

        verify(taskRepository).findById(1L);
        verify(taskMapper).taskToTaskDto(any(Task.class));
        assertEquals(taskDto, result);
    }

    @Nested
    class WhenUpdateTask {
        @Test
        void success() {
            Long taskId = 1L;
            TaskDto updatedTaskDto = new TaskDto(taskId, "Updated Title", "Updated Description", 2L);
            Task existingTask = new Task(taskId, "Existing Title", "Existing Description", 1L);
            Task updatedTask = new Task(taskId, updatedTaskDto.title(), updatedTaskDto.description(), updatedTaskDto.userId());

            doReturn(Optional.of(existingTask)).when(taskRepository).findById(taskId);
            when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
            doReturn(updatedTaskDto).when(taskMapper).taskToTaskDto(any(Task.class));

            Optional<TaskDto> result = taskService.updateTaskById(taskId, updatedTaskDto);

            assertTrue(result.isPresent());
            assertEquals(updatedTaskDto, result.get());
            verify(taskRepository, times(2)).findById(taskId);
            verify(taskRepository, times(1)).save(any(Task.class));
            verify(taskMapper, times(1)).taskToTaskDto(updatedTask);
        }


        @Test
        public void throwTaskAlready() {
            // Given
            Long nonExistentId = 999L;
            TaskDto updatedTaskDto = new TaskDto(2L, "Updated Title", "Updated Description", 2L);

            // When and Then
            assertThrows(ResourceNotFoundException.class, () ->
                    taskService.updateTaskById(nonExistentId, updatedTaskDto)
            );
        }
    }


    @Test
    void testDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task(1L, "Title", "Description", 1L)));

        taskService.deleteTask(1L);

        verify(taskRepository).findById(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testGetAllTasks() {
        TaskDto taskDto1 = new TaskDto(1L, "Title1", "Description1", 1L);
        TaskDto taskDto2 = new TaskDto(2L, "Title2", "Description2", 1L);

        when(taskRepository.findAll()).thenReturn(List.of(new Task(1L, "Title1", "Description1", 1L), new Task(2L, "Title2", "Description2", 1L)));
        when(taskMapper.taskToTaskDto(any(Task.class))).thenReturn(taskDto1).thenReturn(taskDto2);

        List<TaskDto> result = taskService.getAllTasks();

        verify(taskRepository).findAll();
        verify(taskMapper, times(2)).taskToTaskDto(any(Task.class));
        assertEquals(List.of(taskDto1, taskDto2), result);
    }
}