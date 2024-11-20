package com.example.jwttoken.service;

import com.example.jwttoken.exception.CommentNotFoundException;
import com.example.jwttoken.exception.UnauthorizedException;
import com.example.jwttoken.model.Comment;
import com.example.jwttoken.model.Role;
import com.example.jwttoken.model.Task;
import com.example.jwttoken.model.User;
import com.example.jwttoken.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final AuthService authenticationService;

    public CommentService(CommentRepository commentRepository,
                          TaskService taskService, AuthService authenticationService) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
        this.authenticationService = authenticationService;
    }

    public Comment createComment(Comment comment, Integer taskId) {
        Task task = taskService.getTaskById(taskId);
        User user = (User) authenticationService.getCurrentUser();
        comment.setUser(user);
        comment.setTask(task);
        return commentRepository.save(comment);
    }

    public void deleteComment(Integer id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new CommentNotFoundException(id));
        User currentUser = (User) authenticationService.getCurrentUser();
        if (!currentUser.getId().equals(comment.getUser().getId())
                && !currentUser.getRole().contains(Role.ADMIN)) {
            throw new UnauthorizedException();
        }
        commentRepository.delete(comment);
    }

}
