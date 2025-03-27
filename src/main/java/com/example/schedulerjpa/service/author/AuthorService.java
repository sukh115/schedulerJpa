package com.example.schedulerjpa.service.author;

import com.example.schedulerjpa.dto.request.CreateAuthorRequestDto;
import com.example.schedulerjpa.dto.request.UpdateAuthorRequestDto;
import com.example.schedulerjpa.dto.response.AuthorResponseDto;
import com.example.schedulerjpa.dto.response.CreateAuthorResponseDto;
import com.example.schedulerjpa.dto.response.UpdateAuthorResponseDto;

public interface AuthorService {

    CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto);

    AuthorResponseDto findByauthorId(Long authorId);

    UpdateAuthorResponseDto updateAuthor(Long authorId, UpdateAuthorRequestDto dto);

    void deleteAuthor(Long authorId);
}
