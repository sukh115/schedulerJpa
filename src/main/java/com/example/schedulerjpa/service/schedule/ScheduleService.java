package com.example.schedulerjpa.service.schedule;

import com.example.schedulerjpa.dto.request.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.response.CreateScheduleResponseDto;

public interface ScheduleService {

    CreateScheduleResponseDto createSchedule(CreateScheduleRequestDto dto, Long authorId);

}
