package com.example.jwttoken.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq",
            sequenceName = "task_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = Integer.MAX_VALUE)
    private String title;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "duedate", nullable = false)
    private LocalDate duedate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User user1;

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments = new LinkedHashSet<>();

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "priority", length = 20)
    private String priority;

}
