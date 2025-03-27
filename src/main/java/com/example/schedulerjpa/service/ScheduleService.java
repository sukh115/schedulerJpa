package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.CreateScheduleResponseDto;
import com.example.schedulerjpa.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedule();
}
