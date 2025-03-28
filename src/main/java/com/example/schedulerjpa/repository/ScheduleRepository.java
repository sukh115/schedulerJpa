package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import com.example.schedulerjpa.entity.Schedule;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long scheduleId) {
        return findById(scheduleId).orElseThrow(() -> new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND));
    }


    @Query("""
    SELECT new com.example.schedulerjpa.dto.response.SchedulePageResponseDto(
        s.title,
        s.contents,
        a.name,
        COUNT(c),
        s.createdDate,
        s.updatedDate
    )
    FROM Schedule s
    JOIN s.author a
    LEFT JOIN Comment c ON c.schedule = s
    GROUP BY s
""")
    Page<SchedulePageResponseDto> findPagedSchedules(Pageable pageable);
}
