package com.brtvsk.lab4.repository.auth;

import java.util.Optional;

import com.brtvsk.lab4.model.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT user FROM UserEntity user "
            + "LEFT JOIN FETCH user.userRole "
            + "WHERE user.login = :login")
    Optional<UserEntity> findByLogin(@Param("login") String login);

}
