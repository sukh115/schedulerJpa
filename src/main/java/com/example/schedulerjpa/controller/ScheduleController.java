package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.CreateScheduleResponseDto;
import com.example.schedulerjpa.dto.ScheduleResponseDto;
import com.example.schedulerjpa.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> save(@Valid @RequestBody CreateScheduleRequestDto dto) {

        CreateScheduleResponseDto createScheduleResponseDto = scheduleService.saveSchedule(dto);
        return new ResponseEntity<>(createScheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDtos = scheduleService.findAllSchedule();

        return new ResponseEntity<>(scheduleResponseDtos, HttpStatus.OK);

    }


}
