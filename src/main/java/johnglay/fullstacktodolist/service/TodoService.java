package johnglay.fullstacktodolist.service;

import johnglay.fullstacktodolist.dto.TodoDTO;
import johnglay.fullstacktodolist.entity.TodoListEntity;
import johnglay.fullstacktodolist.model.Todo;
import johnglay.fullstacktodolist.repository.TodoRepository;
import johnglay.fullstacktodolist.util.DateTimeUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {

    private final DateTimeUtil dateTimeUtil;
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public List<TodoDTO> addTodo(@NonNull String newTodo) {

        // Initialize new todo
        TodoListEntity todo = TodoListEntity.builder()
                .todoId(UUID.randomUUID())
                .todo(newTodo)
                .createdDate(dateTimeUtil.currentDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        // Save to database
        todoRepository.save(todo);

        return getAllTodos();
    }

    public List<TodoDTO> updateTodo(@NonNull Todo updatedTodo) {

        // Get Todo Entity
        TodoListEntity oldTodo = todoRepository.findByTodoId(updatedTodo.getTodoId());

        // Check if todoId exist
        if (oldTodo == null) throw new RuntimeException("Todo doesn't exist.");

        // Update Todo
        TodoListEntity newTodo = TodoListEntity.builder()
                .todoId(oldTodo.getTodoId())
                .todo(updatedTodo.getTodo())
                .createdDate(oldTodo.getCreatedDate())
                .modifiedDate(dateTimeUtil.currentDate())
                .build();

        // save to database
        todoRepository.save(newTodo);

        return getAllTodos();

    }

    public List<TodoDTO> deleteTodo(UUID id) {
        // Get Todo Entity
        TodoListEntity todo = todoRepository.findByTodoId(id);

        // Check if to do exist
        if(todo == null) throw new RuntimeException("Todo doesn't exist");

        // Delete todo
        todoRepository.deleteByTodoId(id);

        return getAllTodos();
    }

    private List<TodoDTO> getAllTodos() {
        List<TodoListEntity> allTodos = todoRepository.findAll();
        List<TodoDTO> updatedList = new ArrayList<>();

        allTodos.forEach(data -> {
            updatedList.add(modelMapper.map(data, TodoDTO.class));
        });

        return updatedList;
    }
}
