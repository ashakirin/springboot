package com.example.async.demo;

import com.example.async.demo.reactive.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskServiceVT {
    private final UserRepositry userRepositry;
    private final TaskRepositry taskRepository;
    private final TaskExternalService taskExternalService;

    public TaskServiceVT(UserRepository userRepository,
                         TaskRepository taskRepository,
                         TaskExternalService taskExternalService) {
        this.userRepositry = userRepository;
        this.taskRepository = taskRepository;
        this.taskExternalService = taskExternalService;
    }

    @Async
    public CompletableFuture<List<String>> submitTasks(String userGroupId) {
        List<Users> users = userRepositry.findByGroupId(userGroupId);
        List<Task> tasks = taskRepository.findActiveTasks(userGroupId);
        List<String> ids = taskExternalService.submitTasks(users, tasks);
        return CompletableFuture.completedFuture(ids);
    }
}
