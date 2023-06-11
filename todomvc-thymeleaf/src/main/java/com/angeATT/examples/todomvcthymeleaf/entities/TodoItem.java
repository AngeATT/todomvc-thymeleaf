package com.angeATT.examples.todomvcthymeleaf.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class TodoItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;
  @NotBlank
  private String title;

  private boolean completed;

  public TodoItem() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public boolean isCompleted() {
    return completed;
  }

  public TodoItem(String title, boolean completed){
    this.title = title;
    this.completed = completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
}
