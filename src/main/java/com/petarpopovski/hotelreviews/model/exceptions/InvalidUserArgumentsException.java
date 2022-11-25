package com.petarpopovski.hotelreviews.model.exceptions;

public class InvalidUserArgumentsException extends RuntimeException {
    public InvalidUserArgumentsException() {
        super("Invalid username/password.");
    }

}
