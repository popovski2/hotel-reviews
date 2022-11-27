package com.petarpopovski.hotelreviews.model.exceptions;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException(Long id){
        super(String.format("User with id %d does not exist", id));
    }
}
