package org.example.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.taskmanager.dto.TaskDto;
import org.example.taskmanager.service.kafka.DispatchService;
import org.example.taskmanager.service.task.TaskServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskRestController.class)
public class TaskRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskServiceImpl taskServiceImpl;

    @MockBean
    private DispatchService dispatchService;

    @Test
    void testCreateTask() throws Exception {
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Description", 1L);

        when(taskServiceImpl.createTask(any(TaskDto.class))).thenReturn(taskDto);

        mockMvc.perform(post(TaskRestController.TASKS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.description", is("Description")));
    }

    @Test
    void testGetTaskById() throws Exception {
        Long taskId = 1L;
        TaskDto taskDto = new TaskDto(taskId, "Test Task", "Description", 1L);
        when(taskServiceImpl.getTaskById(taskId)).thenReturn(taskDto);

        mockMvc.perform(get(TaskRestController.TASKS_PATH_ID, taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.description", is("Description")));
    }

    @Nested
    class WhenUpdateTask {
        @Test
        public void success() throws Exception {
            Long taskId = 1L;
            TaskDto updatedTaskDto = new TaskDto(taskId, "Updated Title", "Updated Description", 1L);

            doReturn(Optional.of(updatedTaskDto))
                    .when(taskServiceImpl)
                    .updateTaskById(taskId, updatedTaskDto);

            mockMvc.perform(put(TaskRestController.TASKS_PATH_ID, taskId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedTaskDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(taskId))
                    .andExpect(jsonPath("$.title").value("Updated Title"))
                    .andExpect(jsonPath("$.description").value("Updated Description"))
                    .andExpect(jsonPath("$.userId").value(1L));
        }

        @Test
        public void notFound() throws Exception {
            Long taskId = 1L;
            TaskDto updatedTaskDto = new TaskDto(taskId, "Updated Title", "Updated Description", 1L);

            doReturn(Optional.empty())
                    .when(taskServiceImpl)
                    .updateTaskById(taskId, updatedTaskDto);

            mockMvc.perform(put(TaskRestController.TASKS_PATH_ID, taskId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedTaskDto)))
                    .andExpect(status().isNotFound());
        }
    }


    @Test
    void testDeleteTask() throws Exception {
        Long taskId = 1L;
        when(taskServiceImpl.deleteTask(taskId)).thenReturn(true);

        mockMvc.perform(delete(TaskRestController.TASKS_PATH_ID, taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode", is("200")))
                .andExpect(jsonPath("$.statusMsg", is("Request processed successfully")));
    }

    @Test
    void testGetAllTasks() throws Exception {
        List<TaskDto> tasks = Arrays.asList(
                new TaskDto(1L, "Task 1", "Description", 1L),
                new TaskDto(2L, "Task 2", "Description", 2L)
        );
        when(taskServiceImpl.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get(TaskRestController.TASKS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Task 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Task 2")));
    }
}