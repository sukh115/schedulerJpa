package com.example.schedulerjpa.service.author;

import com.example.schedulerjpa.dto.request.CreateAuthorRequestDto;
import com.example.schedulerjpa.dto.request.UpdateAuthorRequestDto;
import com.example.schedulerjpa.dto.response.AuthorResponseDto;
import com.example.schedulerjpa.dto.response.CreateAuthorResponseDto;
import com.example.schedulerjpa.dto.response.UpdateAuthorResponseDto;

/**
 * 작성자(Author) 관련 비즈니스 로직을 정의하는 서비스 인터페이스
 *
 * <p>회원가입, 단일 조회, 수정, 삭제 기능을 제공</p>
 */
public interface AuthorService {

    /**
     * 작성자 회원가입을 처리
     *
     * @param dto 회원가입 요청 DTO
     * @return 생성된 작성자 정보를 담은 응답 DTO
     */
    CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto);

    /**
     * 작성자 ID로 작성자 정보를 조회
     *
     * @param authorId 조회할 작성자 ID
     * @return 조회된 작성자 응답 DTO
     */
    AuthorResponseDto findByauthorId(Long authorId);

    /**
     * 작성자 정보를 수정
     *
     * @param authorId 수정 대상 작성자 ID
     * @param dto      수정 요청 DTO
     * @return 수정된 작성자 응답 DTO
     */
    UpdateAuthorResponseDto updateAuthor(Long authorId, UpdateAuthorRequestDto dto);

    /**
     * 작성자 정보를 삭제
     *
     * @param authorId 삭제할 작성자 ID
     */
    void deleteAuthor(Long authorId);
}
