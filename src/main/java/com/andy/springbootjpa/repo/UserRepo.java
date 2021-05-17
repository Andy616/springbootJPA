package com.andy.springbootjpa.repo;

import com.andy.springbootjpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    User findById(long id);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.password = ?2")
    User findByEmailAndPassword(String email, String password);
}
