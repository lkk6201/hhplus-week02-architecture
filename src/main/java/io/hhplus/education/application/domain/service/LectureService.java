package io.hhplus.education.application.domain.service;

import io.hhplus.education.application.domain.dto.LectureInfoDto;
import io.hhplus.education.application.domain.dto.LectureRegistrationDto;
import io.hhplus.education.application.domain.dto.LectureScheduleDto;
import io.hhplus.education.application.domain.repository.LectureRepository;
import io.hhplus.education.presentation.dto.LectureRegistrationResponseDto;
import io.hhplus.education.presentation.dto.LectureRequestDto;
import io.hhplus.education.presentation.dto.LectureScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public LectureInfoDto getLectureById(Long lectureId) {
        return lectureRepository.findLectureById(lectureId);
    }

    public LectureScheduleDto getLectureScheduleById(Long lectureScheduleId) {
        return lectureRepository.findLectureScheduleById(lectureScheduleId);
    }

    public List<LectureScheduleResponseDto> getAvailableLectureByStudentId(Long studentId, LocalDateTime targetDate) {
        return lectureRepository.findAvailableLectureByUserId(studentId, targetDate);
    }

    public List<LectureRegistrationResponseDto> getRegisteredLecturesByStudentId(Long studentId) {
        return lectureRepository.findRegisteredLecturesByStudentId(studentId);
    }

    public LectureRegistrationDto registerLecture(LectureRequestDto lectureRequestDto) {
        return lectureRepository.registerLecture(lectureRequestDto);
    }

    public void decrementRemainCapacity(Long lectureScheduleId) {
        lectureRepository.decrementRemainCapacity(lectureScheduleId);
    }
}
