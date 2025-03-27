package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.CreateScheduleResponseDto;

public interface ScheduleService {
    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto dto);
}
