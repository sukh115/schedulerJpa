package com.example.schedulerjpa.service.schedule;

import com.example.schedulerjpa.dto.request.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.request.UpdateScheduleRequestDto;
import com.example.schedulerjpa.dto.response.CreateScheduleResponseDto;
import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import com.example.schedulerjpa.dto.response.ScheduleResponseDto;
import com.example.schedulerjpa.dto.response.UpdateScheduleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 일정(Schedule) 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 */
public interface ScheduleService {

    /**
     * 일정을 등록
     *
     * @param dto      일정 생성 요청 DTO
     * @param authorId 작성자 ID
     * @return 생성된 일정 응답 DTO
     */
    CreateScheduleResponseDto createSchedule(CreateScheduleRequestDto dto, Long authorId);

    /**
     * 전체 일정을 조회
     *
     * @return 일정 응답 DTO 리스트
     */
    List<ScheduleResponseDto> findByAllSchedule();

    /**
     * 일정 ID로 단일 일정을 조회
     *
     * @param scheduleId 조회할 일정 ID
     * @return 일정 응답 DTO
     */
    ScheduleResponseDto findByScheduleId(Long scheduleId);

    /**
     * 일정을 수정
     *
     * @param scheduleId 수정할 일정 ID
     * @param dto        수정 요청 DTO
     * @param authorId   로그인된 작성자 ID
     * @return 수정된 일정 응답 DTO
     */
    UpdateScheduleResponseDto updateSchedule(Long scheduleId, UpdateScheduleRequestDto dto, Long authorId);

    /**
     * 일정을 삭제
     *
     * @param scheduleId 삭제할 일정 ID
     * @param authorId   로그인된 작성자 ID
     */
    void deleteSchedule(Long scheduleId, Long authorId);

    /**
     * 페이징 처리된 일정 목록을 조회
     *
     * @param page 조회할 페이지 번호 (0부터 시작)
     * @param size 한 페이지에 포함될 항목 수
     * @return 일정 페이지 응답 DTO
     */
    Page<SchedulePageResponseDto> findAllSchedulePaged(int page, int size);


    Page<SchedulePageResponseDto> searchSchedulesByKeyword(String keyword, Pageable pageable);

}
