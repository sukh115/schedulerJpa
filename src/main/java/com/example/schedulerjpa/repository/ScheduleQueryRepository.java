package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 일정(Schedule) ScheduleRepository에 대한 QueryDSL리포지토리 인터페이스입니다.
 */
public interface ScheduleQueryRepository {
    Page<SchedulePageResponseDto> searchSchedulesByKeyword(String keyword, Pageable pageable);

}
