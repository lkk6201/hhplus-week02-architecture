package io.hhplus.education.application.domain.dto;

import io.hhplus.education.infra.entity.LectureRegistration;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LectureRegistrationDto {

    private Long id;
    private Long studentId;
    private Long lectureId;
    private Long lectureScheduleId;
    private LocalDateTime registrationDate;

    public static LectureRegistrationDto fromEntity(LectureRegistration lectureRegistration) {
        return LectureRegistrationDto.builder()
                .id(lectureRegistration.getId())
                .studentId(lectureRegistration.getStudentId())
                .lectureId(lectureRegistration.getLectureId())
                .lectureScheduleId(lectureRegistration.getLectureScheduleId())
                .registrationDate(lectureRegistration.getCreatedAt())
                .build();
    }
}
