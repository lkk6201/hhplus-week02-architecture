package io.hhplus.education.application.domain.repository;

import io.hhplus.education.application.domain.dto.LectureInfoDto;
import io.hhplus.education.application.domain.dto.LectureRegistrationDto;
import io.hhplus.education.application.domain.dto.LectureScheduleDto;
import io.hhplus.education.presentation.dto.LectureRegistrationResponseDto;
import io.hhplus.education.presentation.dto.LectureRequestDto;
import io.hhplus.education.presentation.dto.LectureScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureRepository {

    LectureInfoDto findLectureById(Long lectureId);

    LectureRegistrationDto registerLecture(LectureRequestDto lectureRequestDto);

    List<LectureScheduleResponseDto> findAvailableLectureByUserId(Long studentId, LocalDateTime targetDate);

    List<LectureRegistrationResponseDto> findRegisteredLecturesByStudentId(Long studentId);

    LectureScheduleDto findLectureScheduleById(Long lectureScheduleId);

    void decrementRemainCapacity(Long lectureScheduleId);
}
