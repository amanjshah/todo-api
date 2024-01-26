package com.aman.rest.todo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.aman.rest.exception.TodoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;

	private static List<Todo> todos = new ArrayList<>();
	
	private static Long todosCount = 0L;
	
	static {
		todos.add(new Todo(++todosCount, "aman","Finish Spring Boot course",
							LocalDate.now().plusYears(1), false ));
		todos.add(new Todo(++todosCount, "aman","Finish reading UDS",
				LocalDate.now().plusYears(1), false ));
		todos.add(new Todo(++todosCount, "aman","Learn AWS",
				LocalDate.now().plusYears(3), false ));
	}
	
	public List<Todo> findByUsername(String username){
		return todoRepository.findByUsername(username);
	}
	
	public Todo addTodo(String username, String description, LocalDate targetDate, boolean done) {
		Todo todo = new Todo(++todosCount,username,description,targetDate,done);
		todos.add(todo);
		return todo;
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