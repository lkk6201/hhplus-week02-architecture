package io.hhplus.education.presentation.dto;

import io.hhplus.education.infra.entity.LectureRegistration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureRequestDto {
    private Long lectureId;
    private Long lectureScheduleId;
    private Long studentId;

    public static LectureRegistration toEntity(LectureRequestDto lectureRequestDto) {
        return LectureRegistration.builder()
                .lectureId(lectureRequestDto.getLectureId())
                .lectureScheduleId(lectureRequestDto.getLectureScheduleId())
                .studentId(lectureRequestDto.getStudentId())
                .build();
    }
}
