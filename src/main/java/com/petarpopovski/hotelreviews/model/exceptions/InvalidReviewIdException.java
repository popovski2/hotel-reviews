package com.petarpopovski.hotelreviews.model.exceptions;

public class InvalidReviewIdException extends RuntimeException{
    public InvalidReviewIdException(Long id){
        super(String.format("Review with id %d does not exist", id));
    }
}
