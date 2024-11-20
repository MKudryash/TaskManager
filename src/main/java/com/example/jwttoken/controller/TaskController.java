package com.example.jwttoken.controller;

import com.example.jwttoken.exception.ApiError;
import com.example.jwttoken.exception.InvalidPriorityException;
import com.example.jwttoken.exception.InvalidStatusException;
import com.example.jwttoken.model.Role;
import com.example.jwttoken.request.CommentRequest;
import com.example.jwttoken.request.TaskCreateRequest;
import com.example.jwttoken.request.TaskUpdateAdminRequest;
import com.example.jwttoken.request.TaskUpdateUserRequest;
import com.example.jwttoken.service.AuthService;
import com.example.jwttoken.security.JwtAuthentication;
import com.example.jwttoken.service.TaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Задачи")
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class TaskController {

    private final AuthService authService;
    private final TaskService taskService;

    @Operation(summary = "Создание задачи. Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("task")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> createTask(@RequestBody TaskCreateRequest taskCreateRequest) throws AuthException {
        try {
            final JwtAuthentication authInfo = authService.getAuthInfo();
            taskService.createTask(taskCreateRequest, authInfo.getUsername());
            return ResponseEntity.ok("Задача успешно создана");
        } catch (AuthException | InvalidStatusException | InvalidPriorityException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    @Operation(summary = "Редактирование задачи администратором. Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("task/{idTask}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateTask(@RequestBody TaskUpdateAdminRequest updateTask, @PathVariable("idTask") Integer idTask) throws AuthException {
        try {
            taskService.updateTask(idTask, updateTask);
            return ResponseEntity.ok("Задача успешно изменена");
        } catch (AuthException | InvalidStatusException |InvalidPriorityException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    @Operation(summary = "Редактирование статуса задачи. Доступен только авторизованным пользователям с ролью USER")
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("task/{idTask}/status")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateTaskStatus(@PathVariable Integer idTask, @RequestBody TaskUpdateUserRequest updateTask) {
        try {
            if (taskService.isExecutor(authService.getAuthInfo().getUsername(), idTask)) {
                taskService.updateTaskStatus(idTask, updateTask);
                return ResponseEntity.ok("Задача успешно изменена");
            } else {
                return ResponseEntity.badRequest().body(new ApiError("Недостаточно прав"));
            }

        } catch (AuthException | InvalidStatusException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }


    @Operation(summary = "Добавление комментария. USER - может добавлять комментарии только к свои задачам, ADMIN - к любой")
    @PostMapping("task/{idTask}/comment")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> addComment(@PathVariable Integer idTask, @RequestBody CommentRequest commentRequest) {
        try {
            if (authService.getAuthInfo().getAuthorities().contains(Role.ADMIN)) {
                taskService.addComment(idTask, commentRequest.getComment(), authService.getAuthInfo().getUsername());
                return ResponseEntity.ok("Комментарий успешно добавлен к задаче");
            } else if (taskService.isExecutor(authService.getAuthInfo().getUsername(), idTask)) {
                taskService.addComment(idTask, commentRequest.getComment(), authService.getAuthInfo().getUsername());
                return ResponseEntity.ok("Комментарий успешно добавлен к задаче");
            } else {
                return ResponseEntity.badRequest().body(new ApiError("Недостаточно прав"));
            }

        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    @Operation(summary = "Просмотр список задач автора. Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("task/author/{authorName}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> findAllByAuthorName(@PathVariable String authorName, Pageable pageable) {
        try {
            return ResponseEntity.ok(taskService.findAllByAuthorName(authorName, pageable));
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    @Operation(summary = "Список задач исполнителя. Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("task/execute/{executeName}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> findAllByExecuteName(@PathVariable String executeName, Pageable pageable) {
        try {
            return ResponseEntity.ok(taskService.findAllByExecuteName(executeName, pageable));
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    @Operation(summary = "Удаление задачи. Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("task/{idTask}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> deleteTask(@PathVariable Integer idTask) throws AuthException {
        try {
            taskService.deleteTask(idTask);
            return ResponseEntity.ok("Задача успешно удалена");
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }
}