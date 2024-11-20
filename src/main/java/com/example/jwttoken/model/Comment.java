package com.example.jwttoken.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    @SequenceGenerator(name = "comment_id_seq",
            sequenceName = "comment_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDate date;


    @JsonIgnoreProperties({"password", "role", "firstName", "lastName", "enabled", "login","id", "authorities", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}