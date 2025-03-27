package com.example.schedulerjpa.service.schedule;

import com.example.schedulerjpa.dto.request.CreateScheduleRequestDto;
import com.example.schedulerjpa.dto.response.CreateScheduleResponseDto;
import com.example.schedulerjpa.dto.response.ScheduleResponseDto;
import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.entity.Schedule;
import com.example.schedulerjpa.repository.AuthorRepository;
import com.example.schedulerjpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    @Override
    public CreateScheduleResponseDto createSchedule(CreateScheduleRequestDto dto, Long authorId) {
        Author author = authorRepository.findByIdOrElseThrow(authorId);

        Schedule schedule = new Schedule(author, dto.getTitle(), dto.getContents());
        Schedule save = scheduleRepository.save(schedule);

        return new CreateScheduleResponseDto(save.getScheduleId(), save.getTitle(), save.getContents(), author.getName());
    }

    @Override
    public List<ScheduleResponseDto> findByAllSchedule() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getTitle(),
                        schedule.getContents(),
                        schedule.getAuthor().getName(),
                        schedule.getUpdatedDate()
                ))
                .toList();
    }

    @Override
    public ScheduleResponseDto findByScheduleId(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        return new ScheduleResponseDto(schedule.getTitle(), schedule.getContents(), schedule.getAuthor().getName(), schedule.getUpdatedDate());
    }
}
