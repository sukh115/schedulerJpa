package com.example.schedulerjpa.service.comment;

import com.example.schedulerjpa.dto.request.CreateCommentRequestDto;
import com.example.schedulerjpa.dto.request.UpdateCommentRequestDto;
import com.example.schedulerjpa.dto.response.CommentResponseDto;
import com.example.schedulerjpa.dto.response.CreateCommentResponseDto;
import com.example.schedulerjpa.dto.response.UpdateCommentReponseDto;
import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.entity.Comment;
import com.example.schedulerjpa.entity.Schedule;
import com.example.schedulerjpa.repository.AuthorRepository;
import com.example.schedulerjpa.repository.CommentRepository;
import com.example.schedulerjpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link CommentService} 구현체로, 댓글의 등록, 조회, 수정, 삭제에 대한 비즈니스 로직을 처리
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AuthorRepository authorRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글을 생성
     *
     * @param dto        댓글 생성 요청 DTO
     * @param authorId   댓글 작성자 ID
     * @param scheduleId 댓글이 속한 일정 ID
     * @return 생성된 댓글 정보 DTO
     */
    @Override
    public CreateCommentResponseDto createComment(CreateCommentRequestDto dto, Long authorId, Long scheduleId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(author, schedule, dto.getContent());
        Comment save = commentRepository.save(comment);

        return new CreateCommentResponseDto(save.getCommentId(), save.getAuthor().getName(), save.getSchedule().getTitle(), save.getContent(), save.getCreatedDate(), save.getUpdatedDate());
    }

    /**
     * 특정 일정에 등록된 댓글 목록을 조회
     *
     * @param scheduleId 조회할 일정 ID
     * @return 댓글 응답 DTO 목록
     */
    @Override
    public List<CommentResponseDto> findBySchedule_ScheduleId(Long scheduleId) {
        List<Comment> comments = commentRepository.findBySchedule_ScheduleId(scheduleId);

        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getAuthor().getName(),
                        comment.getContent(),
                        comment.getUpdatedDate()
                ))
                .toList();
    }

    /**
     * 댓글을 수정합니다. 작성자 본인인지, 해당 일정에 속한 댓글인지 검증 후 내용을 수정
     *
     * @param dto        수정할 댓글 요청 DTO
     * @param commentId  댓글 ID
     * @param authorId   작성자 ID
     * @param scheduleId 일정 ID
     * @return 수정된 댓글 정보 DTO
     */
    @Override
    @Transactional
    public UpdateCommentReponseDto updateComment(UpdateCommentRequestDto dto, Long commentId, Long authorId, Long scheduleId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        comment.isAuthorId(authorId);
        comment.isScheduleId(scheduleId);
        comment.update(dto.getContent());

        return new UpdateCommentReponseDto(comment.getAuthor().getName(), comment.getContent(), comment.getUpdatedDate());
    }

    /**
     * 댓글을 삭제 작성자 본인인지, 해당 일정에 속한 댓글인지 검증 후 삭제
     *
     * @param commentId  댓글 ID
     * @param authorId   작성자 ID
     * @param scheduleId 일정 ID
     */
    @Override
    public void deleteComment(Long commentId, Long authorId, Long scheduleId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        comment.isAuthorId(authorId);
        comment.isScheduleId(scheduleId);
        commentRepository.delete(comment);
    }

    /**
     * 해당 일정 아래 댓글 전체 삭제
     *
     * @param scheduleId    댓글이 속한 일정 ID
     * @param authorId      작성자 ID
     */
    @Override
    @Transactional
    public void deleteALlCommentsBySchedule(Long scheduleId, Long authorId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        schedule.isAuthorId(authorId);

        List<Long> comments = commentRepository.findBySchedule_ScheduleId(scheduleId)
                .stream()
                .map(Comment::getCommentId)
                .toList();

        commentRepository.deleteAllByIdInBatch(comments);
    }
}
