package com.aman.rest.todo;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TodoController {

    private TodoService todoService;

    @GetMapping("/users/{username}/todos")
    public List<Todo> getTodoList(@PathVariable String username) {
        return todoService.findByUsername(username);
    }

    @GetMapping("/users/{username}/todos/{id}")
    public Todo getTodoItem(@PathVariable String username, @PathVariable Long id) {
        return todoService.findById(id);
    }

    @DeleteMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable String username, @PathVariable Long id) {
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{username}/todos")
    public Todo createTodoItem(@PathVariable String username, @RequestBody Todo todo) {
        return todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.getIsDone());
    }

    @PutMapping("/users/{username}/todos/{id}")
    public Todo updateTodoItem(@PathVariable String username, @PathVariable Long id, @RequestBody Todo todo) {
        todoService.updateTodo(todo);
        return todo;
    }

}
