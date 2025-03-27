package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    default Author findByIdOrElseThrow(Long authorId) {
        return findById(authorId).orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_NOT_FOUND));
    }
}
