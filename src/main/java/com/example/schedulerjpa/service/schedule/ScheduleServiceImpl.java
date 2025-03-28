package com.example.schedulerjpa.service.schedule;

import com.example.schedulerjpa.dto.request.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.request.UpdateScheduleRequestDto;
import com.example.schedulerjpa.dto.response.CreateScheduleResponseDto;
import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import com.example.schedulerjpa.dto.response.ScheduleResponseDto;
import com.example.schedulerjpa.dto.response.UpdateScheduleResponseDto;
import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.entity.Schedule;
import com.example.schedulerjpa.repository.AuthorRepository;
import com.example.schedulerjpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link ScheduleService} 구현체로, 일정 생성, 조회, 수정, 삭제, 페이징 조회 등
 * 스케줄 관련 비즈니스 로직을 처리
 *
 * <p>작성자 권한 검증을 포함하며, 트랜잭션 처리로 데이터 정합성을 보장</p>
 */
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    /**
     * 일정을 생성
     *
     * @param dto      일정 생성 요청 DTO
     * @param authorId 작성자 ID
     * @return 생성된 일정 정보 응답 DTO
     */
    @Override
    public CreateScheduleResponseDto createSchedule(CreateScheduleRequestDto dto, Long authorId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        Schedule schedule = new Schedule(author, dto.getTitle(), dto.getContents());
        Schedule save = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(save.getScheduleId(), save.getTitle(), save.getContents(), author.getName());
    }

    /**
     * 전체 일정을 조회
     *
     * @return 일정 응답 DTO 리스트
     */
    @Override
    public List<ScheduleResponseDto> findByAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getTitle(),
                        schedule.getContents(),
                        schedule.getAuthor().getName(),
                        schedule.getUpdatedDate()
                ))
                .toList();
    }

    /**
     * 일정 ID로 단일 일정을 조회합니다.
     *
     * @param scheduleId 조회할 일정 ID
     * @return 일정 응답 DTO
     */
    @Override
    public ScheduleResponseDto findByScheduleId(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        return new ScheduleResponseDto(schedule.getTitle(), schedule.getContents(), schedule.getAuthor().getName(), schedule.getUpdatedDate());
    }

    /**
     * 일정을 수정
     *
     * @param scheduleId 수정 대상 일정 ID
     * @param dto        수정 요청 DTO
     * @param authorId   로그인된 작성자 ID
     * @return 수정된 일정 응답 DTO
     */
    @Override
    @Transactional
    public UpdateScheduleResponseDto updateSchedule(Long scheduleId, UpdateScheduleRequestDto dto, Long authorId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        schedule.isAuthorId(authorId);
        schedule.update(dto.getTitle(), dto.getContents());

        return new UpdateScheduleResponseDto(schedule.getTitle(), schedule.getContents(), schedule.getAuthor().getName(), schedule.getUpdatedDate());
    }

    /**
     * 일정을 삭제
     * <p>작성자 본인인지 검증 후 삭제합니다.</p>
     *
     * @param scheduleId 삭제할 일정 ID
     * @param authorId   로그인된 작성자 ID
     */
    @Override
    public void deleteSchedule(Long scheduleId, Long authorId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        schedule.isAuthorId(authorId);

        scheduleRepository.delete(schedule);

    }

    /**
     * 페이징 처리된 일정 목록을 조회
     *
     * @param page 페이지 번호 (0부터 시작)
     * @param size 한 페이지당 항목 수
     * @return 일정 목록 페이지 DTO
     */
    @Override
    public Page<SchedulePageResponseDto> findAllSchedulePaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedDate"));

        return scheduleRepository.findPagedSchedules(pageable);
    }
}
