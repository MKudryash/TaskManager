package com.example.jwttoken.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskUpdateAdminRequest {
    private String status;
    private String priority;
    private String userName;
}
