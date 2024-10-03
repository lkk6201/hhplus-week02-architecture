package io.hhplus.education.presentation.dto;

import io.hhplus.education.infra.entity.LectureSchedule;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class LectureScheduleResponseDto {
    private Long id;
    private Long lectureId; // 강의 id
    private Long maxCapacity; // 수강 정원
    private Long remainCapacity; // 잔여석
    private LocalDateTime expireDate; // 신청 마감일자

    public static LectureScheduleResponseDto fromEntity(LectureSchedule lectureSchedule) {
        return LectureScheduleResponseDto.builder()
                .id(lectureSchedule.getId())
                .lectureId(lectureSchedule.getLectureId())
                .maxCapacity(lectureSchedule.getMaxCapacity())
                .remainCapacity(lectureSchedule.getRemainCapacity())
                .expireDate(lectureSchedule.getExpireDate())
                .build();
    }
}
