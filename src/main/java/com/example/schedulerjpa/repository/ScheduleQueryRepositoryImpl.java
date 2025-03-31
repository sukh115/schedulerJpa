package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.dto.response.SchedulePageResponseDto;
import com.example.schedulerjpa.entity.QAuthor;
import com.example.schedulerjpa.entity.QComment;
import com.example.schedulerjpa.entity.QSchedule;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class ScheduleQueryRepositoryImpl implements ScheduleQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     *
     * @param keyword  검색어 (제목, 내용, 작성자 이름에 대해 부분 일치 검색)
     * @param pageable 페이지 번호, 사이즈 등의 페이징 정보
     * @return SchedulePageResponseDto 형태의 페이징된 일정 목록
     */
    @Override
    public Page<SchedulePageResponseDto> searchSchedulesByKeyword(String keyword, Pageable pageable) {

        QSchedule schedule = QSchedule.schedule;
        QAuthor author = QAuthor.author;
        QComment comment = QComment.comment;

        // 동적 쿼리 where 조건 생성을 위한 BooleanBuilder
        BooleanBuilder condition = new BooleanBuilder();

        // 검색어가 있을 때, 제목/내용/작성자 이름에서 해당 키워드 포함 여부 검사
        if(keyword != null && !keyword.isBlank()) {
            condition.and(
                    schedule.title.containsIgnoreCase(keyword)
                            .or(schedule.contents.containsIgnoreCase(keyword))
                            .or(author.name.containsIgnoreCase(keyword))
            );
        }

        // 실제 페이징된 결과 목록 조회 (select 절에는 DTO로 바로 매핑)
        List<SchedulePageResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(SchedulePageResponseDto.class,
                        schedule.title,               // 일정 제목
                        schedule.contents,            // 일정 내용
                        author.name,                  // 작성자 이름
                        comment.countDistinct(),      // 댓글 수 (중복 제거)
                        schedule.createdDate,         // 생성일
                        schedule.updatedDate          // 수정일
                ))
                .from(schedule)                         // FROM schedule
                .join(schedule.author, author)          // INNER JOIN author
                .leftJoin(comment).on(comment.schedule.eq(schedule))    // 레프트 조인
                .where(condition)                       // 동적 where 조건 적용
                .groupBy(schedule)                      // COUNT 사용을 위한 그룹핑
                .offset(pageable.getOffset())           // 몇 번째부터 가져올지 (페이지 시작 인덱스)
                .limit(pageable.getPageSize())          // 한 페이지에 몇 개 가져올지
                .orderBy(schedule.updatedDate.desc())   // 최신순 정렬
                .fetch();                               // 결과 리스트로 반환

        // 전체 개수 조회 (페이징의 total count를 위함)
        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(schedule.count())     // 총 개수 select
                        .from(schedule)
                        .join(schedule.author, author)
                        .where(condition)             // 동일한 where 조건 적용
                        .fetchOne()                   // 단일 결과 조회
        ).orElse(0L);                           // 결과가 null일 경우 0으로 처리 (NPE 방지)


        return new PageImpl<>(content, pageable, total);
    }
}
