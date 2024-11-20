package com.example.jwttoken.service;

import com.example.jwttoken.exception.InvalidPriorityException;
import com.example.jwttoken.exception.InvalidStatusException;
import com.example.jwttoken.exception.TaskNotFoundException;
import com.example.jwttoken.model.*;
import com.example.jwttoken.repository.CommentRepository;
import com.example.jwttoken.repository.TaskRepository;
import com.example.jwttoken.repository.UserRepository;
import com.example.jwttoken.request.TaskCreateRequest;
import com.example.jwttoken.request.TaskUpdateAdminRequest;
import com.example.jwttoken.request.TaskUpdateUserRequest;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository,
                       CommentRepository commentRepository,  UserService userService) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Task createTask(@NonNull TaskCreateRequest taskCreateRequest, @NotNull String AuthorName) throws AuthException, InvalidStatusException, InvalidPriorityException {

        User user = userService.getUserByLogin(taskCreateRequest.getUserName())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        User author = userService.getUserByLogin(AuthorName)
                .orElseThrow(() -> new AuthException("Пользователь не найден"));

        final Task newTask = new Task();
        newTask.setUser(user);
        newTask.setUser1(author);
        newTask.setTitle(taskCreateRequest.getTitle());
        newTask.setDescription(taskCreateRequest.getDescription());
        newTask.setDuedate(LocalDate.now());
        try {
            Status.valueOf(taskCreateRequest.getStatus().toUpperCase());
            newTask.setStatus(taskCreateRequest.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException(taskCreateRequest.getStatus());
        }
        try {
            Priority.valueOf(taskCreateRequest.getPriority().toUpperCase());
            newTask.setPriority(taskCreateRequest.getPriority());
        } catch (IllegalArgumentException e) {
            throw new InvalidPriorityException(taskCreateRequest.getPriority());
        }
        newTask.setPriority(taskCreateRequest.getPriority());
        return taskRepository.save(newTask);
    }


    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException(id));
    }

    public boolean isExecutor(String userName, Integer taskId) throws AuthException {
        User executor = userService.getUserByLogin(userName)
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        return task.getUser().getId().equals(executor.getId());
    }

    public void addComment(Integer taskId, String commentText, @NotNull String AuthorName) throws AuthException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        User author = userService.getUserByLogin(AuthorName)
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        Comment comment = new Comment();
        comment.setContent(commentText);
        comment.setTask(task);
        comment.setDate(LocalDate.now());
        comment.setUser(author);
        commentRepository.save(comment);
        task.getComments().add(comment);
        taskRepository.save(task);
    }

    public Task updateTask(Integer taskId, TaskUpdateAdminRequest taskUpdate) throws AuthException, InvalidStatusException, InvalidPriorityException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Status.valueOf(taskUpdate.getStatus().toUpperCase());
        if (taskUpdate.getStatus() != null) {
            try {
                Status.valueOf(taskUpdate.getStatus().toUpperCase());
                task.setStatus(taskUpdate.getStatus());
            } catch (IllegalArgumentException e) {
                throw new InvalidStatusException(taskUpdate.getStatus());
            }
        }
        if (taskUpdate.getPriority() != null) {
            try {
                Priority.valueOf(taskUpdate.getPriority().toUpperCase());
                task.setPriority(taskUpdate.getPriority());
            } catch (IllegalArgumentException e) {
                throw new InvalidPriorityException(taskUpdate.getPriority());
            }
            task.setPriority(taskUpdate.getPriority());
        }
        if (taskUpdate.getUserName() != null) {

            User user = userService.getUserByLogin(taskUpdate.getUserName())
                    .orElseThrow(() -> new AuthException("Пользователь не найден"));
            task.setUser(user);
        }
        return taskRepository.save(task);
    }


    public void deleteTask(Integer taskId) throws AuthException {
        if (taskId == null) {
            throw new AuthException("Отсутствует id задачи");
        }
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        taskRepository.delete(task);
    }

    public Task updateTaskStatus(Integer idTask, TaskUpdateUserRequest updateTask) throws InvalidStatusException {
        Task task = taskRepository.findById(idTask).orElseThrow(() -> new TaskNotFoundException(idTask));
        try {
            Status.valueOf(updateTask.getStatus().toUpperCase());
            task.setStatus(updateTask.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException(updateTask.getStatus());
        }
        return taskRepository.save(task);
    }

    public List<Task> findAllByAuthorName(String authorName, Pageable pageable) throws AuthException {
        User author = userService.getUserByLogin(authorName)
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        return taskRepository.findByUser1(author, pageable);
    }


    public List<Task> findAllByExecuteName(String executeName, Pageable pageable) throws AuthException {
        User execute = userService.getUserByLogin(executeName)
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        return taskRepository.findByUser(execute, pageable);
    }
}

