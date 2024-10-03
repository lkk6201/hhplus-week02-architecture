package io.hhplus.education.presentation.dto;

import io.hhplus.education.infra.entity.LectureRegistration;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LectureRegistrationResponseDto {

    private Long id;
    private String lectureName;
    private String teacherName;
    private Long studentId;
    private Long lectureId;
    private Long lectureScheduleId;
    private LocalDateTime registrationDate;
}
