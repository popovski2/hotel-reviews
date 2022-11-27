package com.petarpopovski.hotelreviews.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    REGULAR_USER , ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
