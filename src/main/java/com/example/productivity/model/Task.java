package com.example.productivity.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class Task {
    private Long id;                    // 기본키
    private String title;               // 할일 제목
    private String description;         // 할일 설명(nullable)
    private Boolean completed;          //완료 여부
    private LocalDateTime createdAt;    //생성일
    private LocalDateTime updatedAt;    //수정일
    private Long userId; // ✅ 추가!
}
