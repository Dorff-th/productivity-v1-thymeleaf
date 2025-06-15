package com.example.productivity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class MonthlyStat {
    private String month;
    private int count;
    private int completed;
}