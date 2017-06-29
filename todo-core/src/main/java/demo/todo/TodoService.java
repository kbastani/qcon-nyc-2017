package demo.todo;

import demo.todo.event.TodoEvent;
import demo.todo.event.TodoEventRepository;
import demo.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoEventRepository todoEventRepository;

    public TodoService(TodoRepository todoRepository,
                       TodoEventRepository todoEventRepository) {
        this.todoRepository = todoRepository;
        this.todoEventRepository = todoEventRepository;
    }

    public Todo getTodo(Long todoId) {
        return todoRepository.findOne(todoId);
    }

    public Todo updateTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public TodoEvent addTodoEvent(Long todoId, TodoEvent todoEvent) {
        Todo todo = getTodo(todoId);
        Assert.notNull(todo, "Todo could not be found");

        todoEvent.setTodoId(todoId);
        todoEvent.setEntity(todo);
        todoEvent = todoEventRepository.saveAndFlush(todoEvent);
        todo.getEvents().add(todoEvent);
        updateTodo(todo);

        return todoEvent;
    }
}
