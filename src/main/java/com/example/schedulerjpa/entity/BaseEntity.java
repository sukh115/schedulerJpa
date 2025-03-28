package com.example.schedulerjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 공통 엔티티 상속 클래스
 *
 * <p>{@code @MappedSuperclass}로 선언되어 있어,
 * 실제 테이블은 생성되지 않고 상속받는 클래스의 필드로 포함됩니다.</p>
 *
 * <p>{@code @EntityListeners(AuditingEntityListener.class)}를 통해
 * Spring Data JPA의 감사를 활성화합니다.</p>
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    // 엔티티 생성 시 자동으로 저장되는 생성일시
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    // 엔티티 수정 시 자동으로 갱신되는 수정일시
    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}