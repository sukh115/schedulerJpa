package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.CreateAuthorRequestDto;
import com.example.schedulerjpa.dto.CreateAuthorResponseDto;
import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto) {
        Author author = new Author(dto);

        Author saveAuthor = authorRepository.save(author);

        return new CreateAuthorResponseDto(saveAuthor.getAuthorId(), saveAuthor.getLoginId(), saveAuthor.getName(), saveAuthor.getPassword(),saveAuthor.getCreatedDate(),saveAuthor.getUpdatedDate());
    }
}
