package com.example.productivity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTaskStatDTO {
    private String username;
    private int total;
    private int completed;
}
