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

/**
 * {@link AuthorService}의 구현 클래스입니다.
 *
 * <p>작성자 회원가입, 조회, 수정, 삭제에 대한 비즈니스 로직을 처리</p>
 * <p>비밀번호 암호화 및 예외 처리를 포함하며, 트랜잭션을 통해 데이터 정합성을 보장</p>
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 작성자 회원가입 로직을 수행
     * <p>
     * 비밀번호 암호화
     * < 엔티티 생성 및 저장
     * </p>
     *
     * @param dto 회원가입 요청 DTO
     * @return 생성된 작성자 정보 응답 DTO
     */
    @Override
    public CreateAuthorResponseDto signUp(CreateAuthorRequestDto dto) {
        // 사용자가 입력한 평문 비밀번호를 암호화합니다.
        String encode = passwordEncoder.encode(dto.getPassword());
        // 암호화된 비밀번호를 포함한 Author 객체를 생성합니다.
        Author author = new Author(dto.getLoginId(), dto.getName(), encode);

        Author saveAuthor = authorRepository.save(author);

        return new CreateAuthorResponseDto(saveAuthor.getAuthorId(), saveAuthor.getLoginId(), saveAuthor.getName(), saveAuthor.getPassword(), saveAuthor.getCreatedDate(), saveAuthor.getUpdatedDate());
    }

    /**
     * 작성자 ID로 작성자를 조회
     *
     * @param authorId 조회할 작성자 ID
     * @return 조회된 작성자 정보 응답 DTO
     */
    @Override
    public AuthorResponseDto findByauthorId(Long authorId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        return new AuthorResponseDto(author.getLoginId(), author.getName(), author.getUpdatedDate());
    }

    /**
     * 작성자 정보를 수정
     *
     * @param authorId 수정 대상 작성자 ID
     * @param dto      수정 요청 DTO
     * @return 수정된 작성자 정보 응답 DTO
     */
    @Override
    @Transactional
    public UpdateAuthorResponseDto updateAuthor(Long authorId, UpdateAuthorRequestDto dto) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        author.update(dto.getLoginId(), dto.getName());

        return new UpdateAuthorResponseDto(author.getLoginId(), author.getName(), author.getUpdatedDate());
    }

    /**
     * 작성자 정보를 삭제
     *
     * @param authorId 삭제 대상 작성자 ID
     */
    @Override
    public void deleteAuthor(Long authorId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        authorRepository.delete(author);
    }
}
