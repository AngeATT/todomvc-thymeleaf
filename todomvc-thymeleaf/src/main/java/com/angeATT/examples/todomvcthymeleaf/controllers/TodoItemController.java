package com.angeATT.examples.todomvcthymeleaf.controllers;

import com.angeATT.examples.todomvcthymeleaf.Exceptions.TodoItemNotFoundException;
import com.angeATT.examples.todomvcthymeleaf.TodomvcThymeleafApplication;
import com.angeATT.examples.todomvcthymeleaf.dto.TodoItemDto;
import com.angeATT.examples.todomvcthymeleaf.entities.ToDoItemFormData;
import com.angeATT.examples.todomvcthymeleaf.entities.TodoItem;
import com.angeATT.examples.todomvcthymeleaf.repositories.TodoItemRepository;
import com.angeATT.examples.todomvcthymeleaf.utils.ListFilter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TodoItemController {
  private TodoItemRepository todoItemRepository;
  private List<TodoItemDto> getTodoItems(){
    return todoItemRepository.findAll()
        .stream().map(todoItem -> new TodoItemDto(
            todoItem.getId(),
            todoItem.getTitle(),
            todoItem.isCompleted()
        )).collect(Collectors.toList());
  }

  public TodoItemController(TodoItemRepository todoItemRepository){
    this.todoItemRepository = todoItemRepository;
  }
  @GetMapping
  public String index(Model model) {
    addAttributesForIndex(model, ListFilter.ALL);
    model.addAttribute("item",new ToDoItemFormData());
    model.addAttribute("todos",getTodoItems());
    model.addAttribute("totalNumberOfItems",todoItemRepository.count());
    model.addAttribute("numberOfActiveItems",getNumberOfActiveItems());
    return "index";
  }

  @PostMapping
  public String addNewTodoItem(@Valid @ModelAttribute("item") ToDoItemFormData toDoItemFormData){
    todoItemRepository.save(new TodoItem(toDoItemFormData.getTitle(),false));
    return "redirect:/";
  };

  @PutMapping("/{id}/toggle")
  String toggleAction(@PathVariable("id") long id) throws TodoItemNotFoundException {
    TodoItem item = todoItemRepository.findById(id).orElseThrow(() -> new TodoItemNotFoundException(id));
    item.setCompleted(!item.isCompleted());
    todoItemRepository.save(item);
    return "redirect:/";
  }

  @DeleteMapping("/{id}")
  String delete(@PathVariable("id") long id) throws TodoItemNotFoundException {
    TodoItem item = todoItemRepository.findById(id).orElseThrow(() -> new TodoItemNotFoundException(id));
    todoItemRepository.delete(item);
    return "redirect:/";
  }

  int getNumberOfActiveItems(){
    //return todoItemRepository.findAll().stream().filter((item) -> !item.isCompleted()).toList().size();
    return todoItemRepository.countAllByCompleted(false);
  }

  private void addAttributesForIndex(Model model,
      ListFilter listFilter) {
    model.addAttribute("item", new ToDoItemFormData());
    model.addAttribute("filter", listFilter);
    model.addAttribute("todos", getTodoItems(listFilter));
    model.addAttribute("totalNumberOfItems", todoItemRepository.count());
    model.addAttribute("numberOfActiveItems", getNumberOfActiveItems());
  }

  private List<TodoItemDto> getTodoItems(ListFilter filter) {
    return switch (filter) {
      case ALL -> convertToDto(todoItemRepository.findAll());
      case ACTIVE -> convertToDto(todoItemRepository.findAllByCompleted(false));
      case COMPLETED -> convertToDto(todoItemRepository.findAllByCompleted(true));
    };
  }
  private List<TodoItemDto> convertToDto(List<TodoItem> todoItems) {
    return todoItems
        .stream()
        .map(todoItem -> new TodoItemDto(todoItem.getId(),
            todoItem.getTitle(),
            todoItem.isCompleted()))
        .collect(Collectors.toList());
  }
}
