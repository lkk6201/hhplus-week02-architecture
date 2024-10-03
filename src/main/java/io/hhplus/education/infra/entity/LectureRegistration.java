package io.hhplus.education.infra.entity;

import io.hhplus.education.infra.entity.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class LectureRegistration extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long studentId;

    private Long lectureId;

    private Long lectureScheduleId;

    @Builder
    public LectureRegistration(Long studentId, Long lectureId, Long lectureScheduleId) {
        this.studentId = studentId;
        this.lectureId = lectureId;
        this.lectureScheduleId = lectureScheduleId;
    }
}
