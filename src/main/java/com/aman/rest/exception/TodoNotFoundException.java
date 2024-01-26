package com.aman.rest.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("The to-do item with id '" + id + "' does not exist. ");
    }
}
