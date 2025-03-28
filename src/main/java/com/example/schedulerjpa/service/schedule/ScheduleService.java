package com.example.schedulerjpa.service.schedule;

import com.example.schedulerjpa.dto.request.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.request.UpdateScheduleRequestDto;
import com.example.schedulerjpa.dto.response.CreateScheduleResponseDto;
import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import com.example.schedulerjpa.dto.response.ScheduleResponseDto;
import com.example.schedulerjpa.dto.response.UpdateScheduleResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScheduleService {

    CreateScheduleResponseDto createSchedule(CreateScheduleRequestDto dto, Long authorId);

    List<ScheduleResponseDto> findByAllSchedule();

    ScheduleResponseDto findByScheduleId(Long scheduleId);

    UpdateScheduleResponseDto updateSchedule(Long scheduleId, UpdateScheduleRequestDto dto, Long authorId);

    void deleteSchedule(Long scheduleId, Long authorId);

    Page<SchedulePageResponseDto> findAllSchedulePaged(int page, int size);

}
