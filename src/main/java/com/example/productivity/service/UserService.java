package com.example.productivity.service;

import com.example.productivity.dto.UserTaskStatDTO;
import com.example.productivity.mapper.UserMapper;
import com.example.productivity.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    //private final PasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(User user) {
        String encrypted = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encrypted);
        userMapper.insert(user);
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }


    // ✅ 사용자별 할 일 통계 반환 (관리자 가능)
    public List<UserTaskStatDTO> getUserTaskStats() {
        List<Map<String, Object>> raw = userMapper.findUserTaskStats();

        return raw.stream()
                .map(row -> new UserTaskStatDTO(
                        (String) row.get("username"),
                        ((Number) row.get("total")).intValue(),
                        ((Number) row.get("completed")).intValue()
                ))
                .collect(Collectors.toList());
    }
}
