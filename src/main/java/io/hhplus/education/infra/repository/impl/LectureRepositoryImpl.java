package io.hhplus.education.infra.repository.impl;

import io.hhplus.education.application.domain.dto.LectureInfoDto;
import io.hhplus.education.application.domain.dto.LectureRegistrationDto;
import io.hhplus.education.application.domain.dto.LectureScheduleDto;
import io.hhplus.education.application.domain.repository.LectureRepository;
import io.hhplus.education.infra.entity.Lecture;
import io.hhplus.education.infra.entity.LectureRegistration;
import io.hhplus.education.infra.entity.LectureSchedule;
import io.hhplus.education.infra.repository.LectureJpaRepository;
import io.hhplus.education.infra.repository.LectureRegistrationJpaRepository;
import io.hhplus.education.infra.repository.LectureScheduleJpaRepository;
import io.hhplus.education.presentation.dto.LectureRegistrationResponseDto;
import io.hhplus.education.presentation.dto.LectureRequestDto;
import io.hhplus.education.presentation.dto.LectureScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LectureRegistrationJpaRepository lectureRegistrationJpaRepository;
    private final LectureScheduleJpaRepository lectureScheduleJpaRepository;

    @Override
    public LectureInfoDto findLectureById(Long lectureId) {
        return lectureJpaRepository.findById(lectureId)
                .map(LectureInfoDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("강의 정보를 찾을 수 없습니다."));
    }

    @Override
    public LectureRegistrationDto registerLecture(LectureRequestDto lectureRequestDto) {
        LectureRegistration lectureRegistration = LectureRequestDto.toEntity(lectureRequestDto);
        lectureRegistrationJpaRepository.save(lectureRegistration);
        return LectureRegistrationDto.fromEntity(lectureRegistration);
    }

    @Override
    public List<LectureScheduleResponseDto> findAvailableLectureByUserId(Long studentId, LocalDateTime targetDate) {
        List<LectureSchedule> lectureSchedules = lectureScheduleJpaRepository.findAvailableLecturesByStudentId(targetDate, studentId);

        return lectureSchedules.stream()
                .map(LectureScheduleResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LectureRegistrationResponseDto> findRegisteredLecturesByStudentId(Long studentId) {
        List<LectureRegistration> registeredLectures = lectureRegistrationJpaRepository.findByStudentId(studentId);

        List<LectureRegistrationResponseDto> registeredLectureInfos = registeredLectures.stream().map(registeredLecture -> {
            Lecture lecture = lectureJpaRepository.findById(registeredLecture.getLectureId())
                    .orElseThrow(() -> new RuntimeException("강의 정보를 찾을 수 없습니다."));
            return LectureRegistrationResponseDto.builder()
                    .id(registeredLecture.getLectureId())
                    .lectureName(lecture.getName())
                    .teacherName(lecture.getTeacherName())
                    .studentId(registeredLecture.getStudentId())
                    .lectureId(registeredLecture.getLectureId())
                    .lectureScheduleId(registeredLecture.getLectureScheduleId())
                    .registrationDate(registeredLecture.getCreatedAt())
                    .build();
        }).toList();

        return registeredLectureInfos;
    }

    @Override
    public LectureScheduleDto findLectureScheduleById(Long lectureScheduleId) {
        return lectureScheduleJpaRepository.findById(lectureScheduleId)
                .map(LectureScheduleDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("강의 스케줄을 찾을 수 없습니다."));
    }

    @Override
    public void decrementRemainCapacity(Long lectureScheduleId) {
        LectureSchedule schedule = lectureScheduleJpaRepository.findById(lectureScheduleId)
                .orElseThrow(() -> new RuntimeException("강의 스케줄을 찾을 수 없습니다."));
        schedule.decrementRemainCapacity();
        lectureScheduleJpaRepository.save(schedule);
    }

    @Override
    public boolean isExistRegisteredLecture(Long lectureScheduleId, Long studentId) {
        return lectureRegistrationJpaRepository.findByLectureScheduleIdAndStudentId(lectureScheduleId, studentId).isPresent();
    }
}
