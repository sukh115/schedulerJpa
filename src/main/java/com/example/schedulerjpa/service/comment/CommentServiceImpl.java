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

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AuthorRepository authorRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Override
    public CreateCommentResponseDto createComment(CreateCommentRequestDto dto, Long authorId, Long scheduleId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(author, schedule, dto.getContent());
        Comment save = commentRepository.save(comment);

        return new CreateCommentResponseDto(save.getCommentId(), save.getAuthor().getName(), save.getSchedule().getTitle(), save.getContent(), save.getCreatedDate(), save.getUpdatedDate());
    }

    @Override
    public List<CommentResponseDto> findBySchedule_ScheduleId(Long ScheduleId) {
        List<Comment> comments = commentRepository.findBySchedule_ScheduleId(ScheduleId);

        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getAuthor().getName(),
                        comment.getContent(),
                        comment.getUpdatedDate()
                ))
                .toList();
    }

    @Override
    @Transactional
    public UpdateCommentReponseDto updateComment(UpdateCommentRequestDto dto, Long commentId, Long authorId, Long scheduleId) {
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        comment.isAuthorId(authorId);
        comment.isScheduleId(scheduleId);
        comment.update(dto.getContent());

        return new UpdateCommentReponseDto(comment.getAuthor().getName(),comment.getContent(),comment.getUpdatedDate());
    }
}
