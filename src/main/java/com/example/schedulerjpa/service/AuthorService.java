package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.CreateAuthorRequestDto;
import com.example.schedulerjpa.dto.CreateAuthorResponseDto;

public interface AuthorService {

    public CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto);
}
