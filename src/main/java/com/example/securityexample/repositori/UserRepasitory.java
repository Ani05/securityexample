package com.example.securityexample.repositori;

import com.example.securityexample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepasitory extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    List<User> findByNameContainsOrSurnameContains(String name, String surname);
}
