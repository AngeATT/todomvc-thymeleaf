package com.angeATT.examples.todomvcthymeleaf.Exceptions;

public class TodoItemNotFoundException extends Exception{
  public TodoItemNotFoundException(long err){
    super(String.format("Erreur, item non trouvé %s",err));
  }

}
