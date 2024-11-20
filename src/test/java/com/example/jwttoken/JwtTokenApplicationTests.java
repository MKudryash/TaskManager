package com.example.jwttoken;

import com.example.jwttoken.model.Priority;
import com.example.jwttoken.model.Status;
import com.example.jwttoken.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtTokenApplicationTests {
/*

    @Autowired
    private TestRestTemplate restTemplate;

    // Заголовок для авторизации
    private final HttpHeaders headers = new HttpHeaders();

    @Test
    @DisplayName("Проверка создания задачи")
    void testCreateTask() {
        String taskUrl = "/api/tasks";

        // Создание новой задачи
        Task task = new Task();
        task.setTitle("Новая задача");
        task.setDescription("Описание новой задачи");
        task.setDuedate(LocalDate.now());
        task.setStatus("В ожидании");
        task.setPriority("Высокий");

        // Отправка запроса POST для создания задачи
        ResponseEntity<Task> response = restTemplate.exchange(URI.create(taskUrl), HttpMethod.POST, new HttpEntity<>(task, headers), Task.class);

        // Проверка статуса ответа
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Получение созданной задачи
        Task createdTask = response.getBody();

        // Проверка созданной задачи
        assertNotNull(createdTask.getId());
        assertEquals(task.getTitle(), createdTask.getTitle());
        assertEquals(task.getDescription(), createdTask.getDescription());
        assertEquals(task.getDuedate(), createdTask.getDuedate());
        assertEquals(task.getStatus(), createdTask.getStatus());
        assertEquals(task.getPriority(), createdTask.getPriority());
    }

    @Test
    @DisplayName("Проверка получения задачи по идентификатору")
    void testGetTaskById() {
        // Создание тестовой задачи
        Task task = createTestTask();

        // Создание URL-адреса для получения задачи
        String taskUrl = "/api/tasks/" + task.getId();

        // Отправка запроса GET для получения задачи
        ResponseEntity<Task> response = restTemplate.getForEntity(taskUrl, Task.class);

        // Проверка статуса ответа
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Получение полученной задачи
        Task retrievedTask = response.getBody();

        // Проверка полученной задачи
        assertEquals(task.getId(), retrievedTask.getId());
        assertEquals(task.getTitle(), retrievedTask.getTitle());
        assertEquals(task.getDescription(), retrievedTask.getDescription());
        assertEquals(task.getDuedate(), retrievedTask.getDuedate());
        assertEquals(task.getStatus(), retrievedTask.getStatus());
        assertEquals(task.getPriority(), retrievedTask.getPriority());
    }

    @Test
    @DisplayName("Проверка удаления задачи")
    void testDeleteTask() {
        // Создание тестовой задачи
        Task task = createTestTask();

        // Отправка запроса DELETE для удаления задачи
        ResponseEntity<Void> response = restTemplate.exchange("/api/tasks/" + task.getId(), HttpMethod.DELETE, null, Void.class);

        // Проверка статуса ответа
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Метод для создания тестовой задачи
    private Task createTestTask() {
        // Создание новой задачи
        Task task = new Task();
        task.setTitle("Тестовая задача");
        task.setDescription("Описание тестовой задачи");
        task.setDuedate(LocalDate.now());
        task.setStatus("В ожидании");
        task.setPriority("Высокий");

        // Отправка запроса POST для создания задачи
        ResponseEntity<Task> response = restTemplate.exchange("/api/tasks", HttpMethod.POST, new HttpEntity<>(task, headers), Task.class);

        // Возврат созданной задачи
        return response.getBody();
    }*/
}
