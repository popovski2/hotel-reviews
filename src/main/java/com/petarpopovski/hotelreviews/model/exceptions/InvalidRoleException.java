package com.petarpopovski.hotelreviews.model.exceptions;

import com.petarpopovski.hotelreviews.model.User;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String displayName) {
        super(String.format("The role of the user %s is not valid for this action.",displayName));
    }
}
