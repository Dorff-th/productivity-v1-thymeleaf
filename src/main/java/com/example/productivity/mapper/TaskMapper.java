package com.example.productivity.mapper;

import com.example.productivity.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface TaskMapper {
    List<Task> findAll();
    Optional<Task> findById(Long id);
    void insert(Task task);
    void update(Task task);
    void delete(Long id);

    List<Task> findByKeywordWithPaging(@Param("keyword") String keyword,
                                       @Param("pageSize") int pageSize,
                                       @Param("offset") int offset);

    int countByKeyword(@Param("keyword") String keyword);

    List<Task> findByUserIdAndKeyword(@Param("userId") Long userId,
                                      @Param("keyword") String keyword,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    int countByUserIdAndKeyword(@Param("userId") Long userId,
                                @Param("keyword") String keyword);


    //통계기능
    int countTotalTasksByUser(Long userId);
    int countCompletedTasksByUser(Long userId);
    List<Map<String, Object>> countTasksGroupedByMonth(Long userId);

    List<Map<String, Object>> countMonthlyTaskStats(Long userId);


}
