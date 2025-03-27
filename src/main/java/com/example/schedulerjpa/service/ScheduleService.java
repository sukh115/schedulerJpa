package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.*;

import java.util.List;

public interface ScheduleService {
    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedule();

    UpdateScheduleResponseDto updateSchedule(Long scheduleId, UpdateScheduleRequestDto dto);

    void deleteSchedule(Long scheduleId);
}
