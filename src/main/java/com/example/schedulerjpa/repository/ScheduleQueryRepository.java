package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleQueryRepository {
    Page<SchedulePageResponseDto> searchSchedulesByKeyword(String keyword, Pageable pageable);

}
