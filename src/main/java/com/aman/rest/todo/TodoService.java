package com.aman.rest.todo;
import java.util.List;
import java.util.Optional;


import com.aman.rest.exception.TodoNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoService {

	private TodoRepository todoRepository;
	
	public List<Todo> findByUsername(String username){
		return todoRepository.findByUsername(username);
	}
	
	public Todo addTodo(String username, Todo todo) {
		todo.setUsername(username);
		todo.setId(null);
		return todoRepository.save(todo);
	}
	
	public void deleteById(Long id) {
		todoRepository.deleteById(id);
	}

	public Todo findById(Long id) {
		return unwrapTodo(id, todoRepository.findById(id));
	}

	public void updateTodo(Todo todo) {
		todoRepository.save(todo);
	}

	private Todo unwrapTodo(Long id, Optional<Todo> optionalTodo) {
		if (optionalTodo.isPresent()) return optionalTodo.get();
		throw new TodoNotFoundException(id);
	}
}