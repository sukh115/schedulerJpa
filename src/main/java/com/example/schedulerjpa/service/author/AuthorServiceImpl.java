package com.example.schedulerjpa.service.author;

import com.example.schedulerjpa.dto.request.CreateAuthorRequestDto;
import com.example.schedulerjpa.dto.request.UpdateAuthorRequestDto;
import com.example.schedulerjpa.dto.response.AuthorResponseDto;
import com.example.schedulerjpa.dto.response.CreateAuthorResponseDto;
import com.example.schedulerjpa.dto.response.UpdateAuthorResponseDto;
import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.repository.AuthorRepository;
import com.example.schedulerjpa.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto) {
        // 사용자가 입력한 평문 비밀번호를 암호화합니다.
        String encode = passwordEncoder.encode(dto.getPassword());
        // 암호화된 비밀번호를 포함한 Author 객체를 생성합니다.
        Author author = new Author(dto.getLoginId(), dto.getName(), encode);

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

    @Override
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        authorRepository.delete(author);
    }
}
