package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import com.example.schedulerjpa.entity.Schedule;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 일정(Schedule) 엔티티에 대한 JPA 리포지토리 인터페이스입니다.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleQueryRepository {

    /**
     * 일정 ID로 일정을 조회
     * 존재하지 않을 경우 {@link CustomException}을 발생
     *
     * @param scheduleId 조회할 일정 ID
     * @return 일정 엔티티
     * @throws CustomException 존재하지 않는 경우 SCHEDULE_NOT_FOUND 예외 발생
     */
    default Schedule findByIdOrElseThrow(Long scheduleId) {
        return findById(scheduleId).orElseThrow(() -> new CustomException(ExceptionCode.SCHEDULE_NOT_FOUND));
    }

    /**
     * 일정 목록을 페이징 처리하여 조회
     * <p>각 일정의 제목, 내용, 작성자 이름, 댓글 수, 생성일자, 수정일자를 포함한 DTO를 제공</p>
     *
     * @param pageable 페이징 정보 (페이지 번호, 사이즈 등)
     * @return 일정 목록 페이지
     */
    @Query("""
                SELECT new com.example.schedulerjpa.dto.response.SchedulePageResponseDto(
                    s.title,
                    s.contents,
                    a.name,
                    COUNT(c),
                    s.createdDate,
                    s.updatedDate
                )
                FROM Schedule s
                JOIN s.author a
                LEFT JOIN Comment c ON c.schedule = s
                GROUP BY s
            """)
    Page<SchedulePageResponseDto> findPagedSchedules(Pageable pageable);
}
