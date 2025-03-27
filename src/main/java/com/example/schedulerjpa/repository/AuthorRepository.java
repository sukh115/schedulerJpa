package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
