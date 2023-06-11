package com.angeATT.examples.todomvcthymeleaf.dto;

public record TodoItemDto(
    Long id, String title, boolean isCompleted
) {

}
