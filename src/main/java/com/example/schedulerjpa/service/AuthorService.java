package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.AuthorResponseDto;
import com.example.schedulerjpa.dto.CreateAuthorRequestDto;
import com.example.schedulerjpa.dto.CreateAuthorResponseDto;

public interface AuthorService {

    CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto);

    AuthorResponseDto findByauthorId(Long authorId);
}
