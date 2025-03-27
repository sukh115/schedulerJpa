package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.*;

public interface AuthorService {

    CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto);

    AuthorResponseDto findByauthorId(Long authorId);

    UpdateAuthorResponseDto updateAuthor(Long authorId, UpdateAuthorRequestDto dto);

    void deleteAuthor(Long authorId);
}
