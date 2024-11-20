package com.example.jwttoken.repository;

import com.example.jwttoken.model.Task;
import com.example.jwttoken.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByUser(User user, Pageable pageable);
    List<Task> findByUser(User user);
    List<Task> findByUser1(User user1, Pageable pageable);
    List<Task> findByUser1(User user1);

}