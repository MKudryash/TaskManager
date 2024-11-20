package com.example.jwttoken.repository;

import com.example.jwttoken.model.Comment;
import com.example.jwttoken.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByTask(Task task);

}
