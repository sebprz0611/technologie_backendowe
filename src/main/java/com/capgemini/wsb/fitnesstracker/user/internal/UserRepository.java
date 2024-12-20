package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();

    }

    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :emailFragment, '%'))")
    List<User> findByEmailContainingIgnoreCase(@Param("emailFragment") String emailFragment);

}
