package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.response.CreateScheduleResponseDto;
import com.example.schedulerjpa.service.schedule.ScheduleService;
import com.example.schedulerjpa.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponseDto> createSchedule(
            @RequestBody CreateScheduleRequestDto dto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        Long authorId = (Long) session.getAttribute(SessionConst.LOGIN_AUTHOR);

        CreateScheduleResponseDto createScheduleResponseDto = scheduleService.createSchedule(dto,authorId);

        return new ResponseEntity<>(createScheduleResponseDto, HttpStatus.CREATED);
    }

}
