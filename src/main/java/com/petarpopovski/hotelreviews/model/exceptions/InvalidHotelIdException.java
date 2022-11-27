package com.petarpopovski.hotelreviews.model.exceptions;

public class InvalidHotelIdException extends RuntimeException {
    public InvalidHotelIdException(Long id){
        super(String.format("Hotel with id %d does not exist", id));
    }
}
