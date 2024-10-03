package io.hhplus.education.application.domain.dto;

import io.hhplus.education.infra.entity.Lecture;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LectureInfoDto {
    private Long id;
    private String name;
    private String teacherName;
    private LocalDateTime createdAt;

    public static LectureInfoDto fromEntity(Lecture lecture) {
        return LectureInfoDto.builder()
                .id(lecture.getId())
                .name(lecture.getName())
                .teacherName(lecture.getTeacherName())
                .createdAt(lecture.getCreatedAt())
                .build();
    }

}
