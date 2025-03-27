package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
