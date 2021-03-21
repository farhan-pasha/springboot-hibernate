package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository exposes us with database CRUD operations
//<Entity,Type of primary key (id)

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
