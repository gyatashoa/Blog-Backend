package com.example.blogbackend.repositories;

import com.example.blogbackend.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByEmail(String email);

}
