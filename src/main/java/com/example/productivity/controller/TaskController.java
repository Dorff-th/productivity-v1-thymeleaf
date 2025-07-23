package com.example.productivity.controller;

import com.example.productivity.mapper.TaskMapper;
import com.example.productivity.model.Task;
import com.example.productivity.model.User;
import com.example.productivity.service.TaskService;
import com.example.productivity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Log4j2
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    // 전체 목록
    @GetMapping
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                       @RequestParam(name = "keyword", required = false) String keyword,
                       Model model) {

        log.info("test");

        if (keyword == null) {
            keyword = "";
        }

        Long userId = getCurrentUserId();

        int pageSize = 5;

        List<Task> tasks = taskService.getPagedTasksByUser(userId, keyword, page, pageSize);
        int totalCount = taskService.countTasksByUser(userId, keyword);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("tasks", tasks);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);

        return "task-list";
    }


    // 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    // 등록 처리
    @PostMapping
    public String create(@ModelAttribute Task task) {
        task.setUserId(getCurrentUserId());
        taskService.insert(task);
        return "redirect:/tasks";
    }


    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) throws Exception {

        Task task = taskService.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 데이터 없음!"));

        log.info("\n\n\n\n====task : " + task);

        if (!task.getUserId().equals(getCurrentUserId())) {
            return "redirect:/tasks?unauthorized"; // 또는 403 페이지
        }

        model.addAttribute("task", task);
        return "task-form";
    }


    // 수정 처리
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute Task task) {    // @PathVariable Long id -> @PathVariable("id") Long id 직접수정
        task.setId(id);
        taskService.update(task);
        return "redirect:/tasks";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) throws Exception {
        //Task task = taskService.findById(id);
        Task task = taskService.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 데이터 없음!"));
        if (!task.getUserId().equals(getCurrentUserId())) {
            return "redirect:/tasks?unauthorized";
        }
        taskService.delete(id);
        return "redirect:/tasks";
    }


    // 할일 완료/미완료 토글 API 로 처리
    /*@PatchMapping("/{id}/toggle")
    @ResponseBody
    public String toggleCompleted(@PathVariable("id") Long id) throws Exception {
        //Task task = taskService.findById(id);
        Task task = taskService.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 데이터 없음!"));
        if (task != null) {
            task.setCompleted(!Boolean.TRUE.equals(task.getCompleted()));
            taskService.update(task);
            return task.getCompleted() ? "completed" : "incomplete";
        }
        return "error";
    }*/

    @PostMapping("/{id}/toggle")
    @ResponseBody
    public Map<String, Object> toggleComplete(@PathVariable("id") Long id) throws Exception {   // 멍청한 ChatGTP는 ("id")를 자꾸 빼먹는다.
        //Task task = taskService.findById(id);
        Task task = taskService.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 데이터 없음!"));

        if (!task.getUserId().equals(getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if(task.getCompleted() == null) {
            task.setCompleted(false);
        }

        task.setCompleted(!task.getCompleted());
        taskService.update(task);

        return Map.of("completed", task.getCompleted());
    }


    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // 로그인한 사용자 이름
        User user = userService.findByUsername(username);
        return user.getId();
    }

}
