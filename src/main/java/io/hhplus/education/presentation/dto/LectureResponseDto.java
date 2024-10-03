package io.hhplus.education.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LectureResponseDto {
    private Long id;
    private String name; // 강의명
    private String teacherName; // 강사명
    private LocalDateTime createdAt; // 생성일자
}
