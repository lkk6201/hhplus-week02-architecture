package io.hhplus.education.infra.entity;

import io.hhplus.education.infra.entity.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class LectureSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId; // 강의 id

    private Long maxCapacity; // 수강 정원

    private Long remainCapacity; // 잔여석

    private LocalDateTime expireDate; // 신청 마감일자

    public void decrementRemainCapacity() {
        remainCapacity -= 1;
    }
}
