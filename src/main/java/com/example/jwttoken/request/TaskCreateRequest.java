package com.example.jwttoken.request;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class TaskCreateRequest {
    private String title;
    private String description;
    private String status;
    private String priority;
    private String userName;
}