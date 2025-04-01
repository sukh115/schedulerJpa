package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.CreateAuthorRequestDto;
import com.example.schedulerjpa.dto.request.UpdateAuthorRequestDto;
import com.example.schedulerjpa.dto.response.AuthorResponseDto;
import com.example.schedulerjpa.dto.response.CreateAuthorResponseDto;
import com.example.schedulerjpa.dto.response.UpdateAuthorResponseDto;
import com.example.schedulerjpa.service.author.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 작성자(Author) 관련 요청을 처리하는 컨트롤러입니다.
 *
 * <p>회원가입, 단일 작성자 조회, 수정, 삭제 기능을 제공합니다.</p>
 */
@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    /**
     * 작성자 회원가입 요청을 처리
     *
     * @param dto 회원가입 요청 DTO
     * @return 생성된 작성자 정보와 201(CREATED) 응답
     */
    @PostMapping("/signup")
    public ResponseEntity<CreateAuthorResponseDto> signUp(@Valid @RequestBody CreateAuthorRequestDto dto) {
        CreateAuthorResponseDto createAuthorResponseDto = authorService.signUp(dto);

        return new ResponseEntity<>(createAuthorResponseDto, HttpStatus.CREATED);
    }

    /**
     * 작성자 ID로 단일 작성자 정보를 조회
     *
     * @param authorId 조회할 작성자 ID
     * @return 작성자 정보와 200(OK) 응답
     */
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDto> findAllAuthor(@PathVariable Long authorId) {
        AuthorResponseDto author = authorService.findByAuthorId(authorId);

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    /**
     * 작성자 정보를 수정
     *
     * @param authorId 수정할 작성자 ID
     * @param dto      수정 요청 DTO
     * @return 수정된 작성자 정보와 200(OK) 응답
     */
    @PatchMapping("/{authorId}")
    public ResponseEntity<UpdateAuthorResponseDto> updateAuthor(
            @PathVariable Long authorId,
            @Valid @RequestBody UpdateAuthorRequestDto dto
    ) {
        UpdateAuthorResponseDto updateAuthorResponseDto = authorService.updateAuthor(authorId, dto);

        return new ResponseEntity<>(updateAuthorResponseDto, HttpStatus.OK);
    }

    /**
     * 작성자 정보를 삭제
     *
     * @param authorId 삭제할 작성자 ID
     * @return 200(OK) 응답 (본문 없음)
     */
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
