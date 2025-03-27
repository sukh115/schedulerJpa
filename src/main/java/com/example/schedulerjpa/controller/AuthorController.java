package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.CreateAuthorRequestDto;
import com.example.schedulerjpa.dto.CreateAuthorResponseDto;
import com.example.schedulerjpa.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
