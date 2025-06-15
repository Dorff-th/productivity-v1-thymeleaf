package com.example.productivity.mapper;

import com.example.productivity.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    User findByUsername(@Param("username") String username);

    void insert(User user);

    //사용자멸 stat (관리자 기능)
    List<Map<String, Object>> findUserTaskStats();
}
