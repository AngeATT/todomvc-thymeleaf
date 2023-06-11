package com.angeATT.examples.todomvcthymeleaf.entities;

import jakarta.validation.constraints.NotBlank;

public class ToDoItemFormData {
  @NotBlank
  private String title;
  public String getTitle(){
    return this.title;
  };

  public void setTitle(String title){
    this.title = title;
  }
}
