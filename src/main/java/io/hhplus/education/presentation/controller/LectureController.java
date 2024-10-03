package io.hhplus.education.presentation.controller;

import io.hhplus.education.application.domain.dto.LectureRegistrationDto;
import io.hhplus.education.application.facade.LectureFacade;
import io.hhplus.education.presentation.dto.LectureRegistrationResponseDto;
import io.hhplus.education.presentation.dto.LectureRequestDto;
import io.hhplus.education.presentation.dto.LectureResponseDto;
import io.hhplus.education.presentation.dto.LectureScheduleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    /**
     * 특강 신청
     */
    @PostMapping
    public LectureRegistrationDto registerLecture(@RequestBody LectureRequestDto lectureRequestDto) {
        return lectureFacade.registerLecture(lectureRequestDto);
    }

    /**
     * 특정 유저가 신청 가능한 특강 목록 조회
     */
    @GetMapping("/available/{studentId}")
    public List<LectureScheduleResponseDto> getAvailableLectures(@PathVariable("studentId") Long studentId,
                                                                 @RequestParam("targetDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDate) {
        return lectureFacade.getAvailableLectures(studentId, targetDate);
    }

    /**
     * 특정 유저의 특강 신청 완료 목록 조회
     */
    @GetMapping("/registrations/{studentId}")
    public List<LectureRegistrationResponseDto> getRegisteredLectures(@PathVariable("studentId") Long studentId) {
        return lectureFacade.getRegisteredLectures(studentId);
    }

}
