package com.aman.rest.todo;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    @GetMapping("/users/{username}/todos")
    public List<Todo> getTodoList(@PathVariable String username) {
        return todoService.findByUsername(username);
    }
}
