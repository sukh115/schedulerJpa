package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 작성자(Author) 엔티티에 대한 JPA 리포지토리 인터페이스
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * 로그인 ID로 작성자를 조회합니다.
     *
     * @param loginId 로그인 ID
     * @return Optional 형태의 작성자 객체
     */
    Optional<Author> findByLoginId(String loginId);

    /**
     * 로그인 ID로 작성자를 조회하며,
     * 존재하지 않을 경우 {@link CustomException}을 발생시킵니다.
     *
     * @param loginId 로그인 ID
     * @return 작성자 엔티티
     * @throws CustomException 존재하지 않는 경우 AUTHOR_ID_MISMATCH 예외 발생
     */
    default Author findByLoginIdOrElseThrow(String loginId) {
        return findByLoginId(loginId).orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_ID_MISMATCH));
    }

    /**
     * 작성자 ID로 작성자를 조회하며,
     * 존재하지 않을 경우 {@link CustomException}을 발생시킵니다.
     *
     * @param authorId 작성자 ID
     * @return 작성자 엔티티
     * @throws CustomException 존재하지 않는 경우 AUTHOR_NOT_FOUND 예외 발생
     */
    default Author findByIdOrElseThrow(Long authorId) {
        return findById(authorId).orElseThrow(() -> new CustomException(ExceptionCode.AUTHOR_NOT_FOUND));
    }
}
