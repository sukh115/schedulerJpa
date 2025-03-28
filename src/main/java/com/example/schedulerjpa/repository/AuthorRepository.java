package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByLoginId(String loginId);

    default Author findByLoginIdOrElseThrow(String loginId){
        return findByLoginId(loginId).orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_ID_MISMATCH));
    };

    default Author findByIdOrElseThrow(Long authorId) {
        return findById(authorId).orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_NOT_FOUND));
    }
}
