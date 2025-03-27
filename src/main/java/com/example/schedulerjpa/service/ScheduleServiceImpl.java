package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.*;
import com.example.schedulerjpa.entity.Schedule;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.example.schedulerjpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public CreateScheduleResponseDto saveSchedule(CreateScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto);

        Schedule save = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(save.getScheduleId(), save.getAuthorName(), save.getTitle(), save.getContents());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getAuthorName(),
                        schedule.getTitle(),
                        schedule.getContents(),
                        schedule.getUpdatedDate()
                ))
                .toList();
    }

    @Override
    @Transactional
    public UpdateScheduleResponseDto updateSchedule(Long schduleId, UpdateScheduleRequestDto dto) {

        Schedule schedule = scheduleRepository.findById(schduleId).orElseThrow(() -> new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND));

        schedule.update(dto.getTitle(), dto.getContents());

        return new UpdateScheduleResponseDto(schedule.getAuthorName(), schedule.getTitle(), schedule.getContents(), schedule.getUpdatedDate());
    }
}
