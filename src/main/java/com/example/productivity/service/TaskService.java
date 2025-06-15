package com.example.productivity.service;

import com.example.productivity.dto.MonthlyStat;
import com.example.productivity.mapper.TaskMapper;
import com.example.productivity.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper taskMapper;

    public List<Task> findAll() {
        return taskMapper.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskMapper.findById(id);
    }

    public void insert(Task task) {
        taskMapper.insert(task);
    }

    public void update(Task task) {
        taskMapper.update(task);
    }

    public void delete(Long id) {
        taskMapper.delete(id);
    }

    public List<Task> getTasksByKeyword(String keyword, int pageSize, int page) {
        int offset = (page - 1) * pageSize;
        return taskMapper.findByKeywordWithPaging(keyword, pageSize, offset);
    }

    public int countTasksByKeyword(String keyword) {
        return taskMapper.countByKeyword(keyword);
    }

    public List<Task> getPagedTasksByUser(Long userId, String keyword, int page, int size) {
        int offset = (page - 1) * size;
        return taskMapper.findByUserIdAndKeyword(userId, keyword, offset, size);
    }

    public int countTasksByUser(Long userId, String keyword) {
        return taskMapper.countByUserIdAndKeyword(userId, keyword);
    }


    public int countTotalTasksByUser(Long userId) {
        return taskMapper.countTotalTasksByUser(userId);
    }
    public int countCompletedTasksByUser(Long userId) {
        return taskMapper.countCompletedTasksByUser(userId);
    }

    public List<MonthlyStat> countMonthlyTaskStats(Long userId) {
        List<Map<String, Object>> rawList = taskMapper.countMonthlyTaskStats(userId);

        return rawList.stream()
                .map(row -> new MonthlyStat(
                        (String) row.get("month"),
                        ((Number) row.get("total")).intValue(),
                        ((Number) row.get("completed")).intValue()
                ))
                .toList();
    }


}
