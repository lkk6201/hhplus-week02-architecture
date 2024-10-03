package io.hhplus.education.application.facade;

import io.hhplus.education.application.domain.dto.LectureRegistrationDto;
import io.hhplus.education.application.domain.dto.LectureScheduleDto;
import io.hhplus.education.application.domain.service.LectureService;
import io.hhplus.education.presentation.dto.LectureRegistrationResponseDto;
import io.hhplus.education.presentation.dto.LectureRequestDto;
import io.hhplus.education.presentation.dto.LectureScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;

    /**
     * 특강 신청
     * @param lectureRequestDto
     */
    @Transactional
    public LectureRegistrationDto registerLecture(LectureRequestDto lectureRequestDto) {
        // 이미 수강신청한 특강인지 확인
        List<LectureRegistrationResponseDto> registeredLectures = lectureService.getRegisteredLecturesByStudentId(lectureRequestDto.getStudentId());
        registeredLectures.forEach(dto -> {
           if (dto.getLectureScheduleId().equals(lectureRequestDto.getLectureScheduleId())) {
               throw new RuntimeException("이미 수강 신청한 특강입니다.");
           }
        });

        // 잔여좌석이 없는 경우
        LectureScheduleDto lectureScheduleDto = lectureService.getLectureScheduleById(lectureRequestDto.getLectureId());
        if (lectureScheduleDto.getRemainCapacity() <= 0) {
            throw new RuntimeException("잔여 좌석이 존재하지 않습니다.");
        }

        // 수강신청 처리
        LectureRegistrationDto lectureRegistrationDto = lectureService.registerLecture(lectureRequestDto);
        // 잔여석 감소 처리
        lectureService.decrementRemainCapacity(lectureRequestDto.getLectureScheduleId());

        return lectureRegistrationDto;
    }

    public List<LectureScheduleResponseDto> getAvailableLectures(Long studentId, LocalDateTime targetDate) {
        return lectureService.getAvailableLectureByStudentId(studentId, targetDate);
    }

    public List<LectureRegistrationResponseDto> getRegisteredLectures(Long studentId) {
        return lectureService.getRegisteredLecturesByStudentId(studentId);
    }
}
