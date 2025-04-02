package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.request.UpdateScheduleRequestDto;
import com.example.schedulerjpa.dto.response.CreateScheduleResponseDto;
import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import com.example.schedulerjpa.dto.response.ScheduleResponseDto;
import com.example.schedulerjpa.dto.response.UpdateScheduleResponseDto;
import com.example.schedulerjpa.service.schedule.ScheduleService;
import com.example.schedulerjpa.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일정 관련 요청을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정을 등록
     *
     * @param dto     일정 생성 요청 DTO
     * @param request 현재 HTTP 요청 (세션에서 로그인 작성자 ID 추출)
     * @return 생성된 일정 정보와 201(CREATED) 응답
     */
    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @Valid @RequestBody CreateScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        Long authorId = AuthUtil.getLoginAuthorId();


        CreateScheduleResponseDto createScheduleResponseDto = scheduleService.createSchedule(dto, authorId);

        return new ResponseEntity<>(createScheduleResponseDto, HttpStatus.CREATED);
    }

    /**
     * 모든 일정을 조회
     *
     * @return 전체 일정 목록과 200(OK) 응답
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules() {
        List<ScheduleResponseDto> schedules = scheduleService.findByAllSchedule();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    /**
     * 페이징 처리된 일정 목록을 조회
     *
     * @param page 조회할 페이지 번호 (기본값: 0)
     * @param size 한 페이지당 항목 수 (기본값: 10)
     * @return 페이징된 일정 목록과 200(OK) 응답
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<SchedulePageResponseDto>> findAllSchedulePaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<SchedulePageResponseDto> allSchedulePaged = scheduleService.findAllSchedulePaged(page, size);
        return new ResponseEntity<>(allSchedulePaged, HttpStatus.OK);
    }

    /**
     * 일정 ID로 단일 일정을 조회
     *
     * @param scheduleId 조회할 일정 ID
     * @return 일정 정보와 200(OK) 응답
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findByScheduleId(@PathVariable Long scheduleId) {
        ScheduleResponseDto dto = scheduleService.findByScheduleId(scheduleId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * 일정을 수정
     *
     * @param scheduleId 수정할 일정 ID
     * @param dto        일정 수정 요청 DTO
     * @param request    현재 HTTP 요청 (세션에서 로그인 작성자 ID 추출)
     * @return 수정된 일정 정보와 200(OK) 응답
     */
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        Long authorId = AuthUtil.getLoginAuthorId();

        UpdateScheduleResponseDto updateScheduleResponseDto = scheduleService.updateSchedule(scheduleId, dto, authorId);

        return new ResponseEntity<>(updateScheduleResponseDto, HttpStatus.OK);
    }

    /**
     * 일정을 삭제
     *
     * @param scheduleId 삭제할 일정 ID
     * @param request    현재 HTTP 요청 (세션에서 로그인 작성자 ID 추출)
     * @return 200(OK) 응답 (본문 없음)
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            HttpServletRequest request
    ) {
        Long authorId = AuthUtil.getLoginAuthorId();

        scheduleService.deleteSchedule(scheduleId, authorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 일정 키워드 검색 조회
     *
     * @param keyword 검색 조건
     * @param page    조회할 페이지 번호 (기본값: 0)
     * @param size    한 페이지당 항목 수 (기본값: 10)
     * @return 검색 조건에 맞는 페이징된 일정 목록과 200(OK) 응답
     */
    @GetMapping("/search")
    public ResponseEntity<Page<SchedulePageResponseDto>> searchSchedulesByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updateDate"));
        Page<SchedulePageResponseDto> schedulePageResponseDto = scheduleService.searchSchedulesByKeyword(keyword, pageable);
        return new ResponseEntity<>(schedulePageResponseDto, HttpStatus.OK);
    }

}
