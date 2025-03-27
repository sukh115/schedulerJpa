package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.*;
import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto) {
        Author author = new Author(dto);

        Author saveAuthor = authorRepository.save(author);

        return new CreateAuthorResponseDto(saveAuthor.getAuthorId(), saveAuthor.getLoginId(), saveAuthor.getName(), saveAuthor.getPassword(), saveAuthor.getCreatedDate(), saveAuthor.getUpdatedDate());
    }

    @Override
    public AuthorResponseDto findByauthorId(Long authorId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        return new AuthorResponseDto(author.getLoginId(), author.getName(), author.getUpdatedDate());
    }

    @Override
    @Transactional
    public UpdateAuthorResponseDto updateAuthor(Long authorId, UpdateAuthorRequestDto dto) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        author.update(dto.getLoginId(), dto.getName());

        return new UpdateAuthorResponseDto(author.getLoginId(), author.getName(), author.getUpdatedDate());
    }
}
