package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Schedule;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long scheduleId) {
        return findById(scheduleId).orElseThrow(() -> new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND));
    }
}
