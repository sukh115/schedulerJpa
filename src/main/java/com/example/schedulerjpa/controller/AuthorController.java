package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.*;
import com.example.schedulerjpa.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/signup")
    public ResponseEntity<CreateAuthorResponseDto> signUp(@RequestBody CreateAuthorRequestDto dto) {
        CreateAuthorResponseDto createAuthorResponseDto = authorService.signUp(dto);

        return new ResponseEntity<>(createAuthorResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDto> findAllAuthor(@PathVariable Long authorId) {
        AuthorResponseDto author = authorService.findByauthorId(authorId);

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PatchMapping("/{authorId}")
    public ResponseEntity<UpdateAuthorResponseDto> updateAuthor(
            @PathVariable Long authorId,
            @RequestBody UpdateAuthorRequestDto dto
    ) {
        UpdateAuthorResponseDto updateAuthorResponseDto = authorService.updateAuthor(authorId, dto);

        return new ResponseEntity<>(updateAuthorResponseDto, HttpStatus.OK);
    }
}
