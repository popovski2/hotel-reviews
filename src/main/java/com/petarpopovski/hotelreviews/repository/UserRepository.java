package com.petarpopovski.hotelreviews.repository;

import com.petarpopovski.hotelreviews.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findUserByDisplayName(String displayName);

    User findByEmailAndPassword(String email, String password);

}
