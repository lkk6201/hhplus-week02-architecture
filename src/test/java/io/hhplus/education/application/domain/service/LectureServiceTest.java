package io.hhplus.education.application.domain.service;

import io.hhplus.education.application.domain.dto.LectureInfoDto;
import io.hhplus.education.application.domain.dto.LectureRegistrationDto;
import io.hhplus.education.application.domain.dto.LectureScheduleDto;
import io.hhplus.education.application.domain.repository.LectureRepository;
import io.hhplus.education.presentation.dto.LectureRegistrationResponseDto;
import io.hhplus.education.presentation.dto.LectureRequestDto;
import io.hhplus.education.presentation.dto.LectureScheduleResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    private static final Long MAX_CAPACITY = 30L;

    private LectureInfoDto lectureInfoDto;
    private LectureInfoDto lectureInfoDto2;

    private LectureScheduleDto lectureScheduleDto;
    private LectureScheduleDto lectureScheduleDto2;
    private List<LectureScheduleDto> lectureScheduleDtos;

    private LectureScheduleResponseDto lectureScheduleResponseDto;
    private LectureScheduleResponseDto lectureScheduleResponseDto2;
    private List<LectureScheduleResponseDto> lectureScheduleResponseDtos;

    private LectureRegistrationResponseDto lectureRegistrationResponseDto;
    private LectureRegistrationResponseDto lectureRegistrationResponseDto2;
    private List<LectureRegistrationResponseDto> lectureRegistrationResponseDtos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        lectureInfoDto = LectureInfoDto.builder()
                .id(1L)
                .name("스프링 초급")
                .teacherName("이광규")
                .build();

        lectureInfoDto2 = LectureInfoDto.builder()
                .id(2L)
                .name("스프링 중급")
                .teacherName("이광규")
                .build();

        lectureScheduleDto = LectureScheduleDto.builder()
                .id(1L)
                .lectureId(1L)
                .maxCapacity(MAX_CAPACITY)
                .remainCapacity(MAX_CAPACITY)
                .expireDate(LocalDateTime.now())
                .build();

        lectureScheduleDto2 = LectureScheduleDto.builder()
                .id(1L)
                .lectureId(1L)
                .maxCapacity(MAX_CAPACITY)
                .remainCapacity(MAX_CAPACITY)
                .expireDate(LocalDateTime.now())
                .build();

        lectureScheduleDtos = List.of(lectureScheduleDto, lectureScheduleDto2);

        lectureScheduleResponseDto = LectureScheduleResponseDto.builder()
                .id(1L)
                .lectureId(1L)
                .maxCapacity(MAX_CAPACITY)
                .remainCapacity(MAX_CAPACITY)
                .expireDate(LocalDateTime.now())
                .build();

        lectureScheduleResponseDto2 = LectureScheduleResponseDto.builder()
                .id(2L)
                .lectureId(2L)
                .maxCapacity(MAX_CAPACITY)
                .remainCapacity(10L)
                .expireDate(LocalDateTime.now())
                .build();

        lectureScheduleResponseDtos = List.of(lectureScheduleResponseDto, lectureScheduleResponseDto2);

        lectureRegistrationResponseDto = LectureRegistrationResponseDto.builder()
                .id(1L)
                .lectureName("스프링 초급")
                .teacherName("이광규")
                .studentId(1L)
                .lectureId(1L)
                .lectureScheduleId(1L)
                .registrationDate(LocalDateTime.now())
                .build();

        lectureRegistrationResponseDto2 = LectureRegistrationResponseDto.builder()
                .id(1L)
                .lectureName("스프링 중급")
                .teacherName("이광규")
                .studentId(1L)
                .lectureId(2L)
                .lectureScheduleId(2L)
                .registrationDate(LocalDateTime.now())
                .build();

        lectureRegistrationResponseDtos = List.of(lectureRegistrationResponseDto, lectureRegistrationResponseDto2);
    }

    @Test
    @DisplayName("특강 정보 조회 성공")
    void getLectureInfoSuccess() {
        when(lectureRepository.findLectureById(1L)).thenReturn(lectureInfoDto);

        LectureInfoDto resultLecture = lectureService.getLectureById(1L);

        assertEquals(lectureInfoDto, resultLecture);
        verify(lectureRepository).findLectureById(1L);
    }

    @Test
    @DisplayName("특강 정보 존재하지 않음")
    void getLectureInfoFailure() {
        when(lectureRepository.findLectureById(4L)).thenThrow(new RuntimeException("강의 정보를 찾을 수 없습니다."));

        RuntimeException resultException = assertThrows(RuntimeException.class, () -> lectureService.getLectureById(4L));

        verify(lectureRepository).findLectureById(4L);
        assertEquals("강의 정보를 찾을 수 없습니다.", resultException.getMessage());
    }

    @Test
    @DisplayName("특강 스케줄 조회 성공")
    void getLectureScheduleSuccess() {

        when(lectureRepository.findLectureScheduleById(1L)).thenReturn(lectureScheduleDto);

        LectureScheduleDto resultLectureSchedule = lectureService.getLectureScheduleById(1L);

        assertEquals(lectureScheduleDto, resultLectureSchedule);
        verify(lectureRepository).findLectureScheduleById(1L);

    }

    @Test
    @DisplayName("특강 스케줄이 존재하지 않음")
    void getLectureScheduleFailure() {
        when(lectureRepository.findLectureScheduleById(4L)).thenThrow(new RuntimeException("강의 스케줄을 찾을 수 없습니다."));

        RuntimeException resultException = assertThrows(RuntimeException.class, () -> lectureService.getLectureScheduleById(4L));

        verify(lectureRepository).findLectureScheduleById(4L);
        assertEquals("강의 스케줄을 찾을 수 없습니다.", resultException.getMessage());
    }

    @Test
    @DisplayName("신청 가능한 특강 목록 조회 성공")
    void getAvailableLecturesSuccess() {
        LocalDateTime now = LocalDateTime.now();
        when(lectureRepository.findAvailableLectureByUserId(1L, now)).thenReturn(lectureScheduleResponseDtos);

        List<LectureScheduleResponseDto> resultLectures = lectureService.getAvailableLectureByStudentId(1L, now);

        assertEquals(lectureScheduleResponseDtos, resultLectures);
        verify(lectureRepository).findAvailableLectureByUserId(1L, now);

    }

    @Test
    @DisplayName("신청 완료한 특강 목록 조회 성공")
    void getRegisteredLecturesSuccess() {
        when(lectureRepository.findRegisteredLecturesByStudentId(1L)).thenReturn(lectureRegistrationResponseDtos);

        List<LectureRegistrationResponseDto> resultLectures = lectureService.getRegisteredLecturesByStudentId(1L);

        assertEquals(lectureRegistrationResponseDtos, resultLectures);
        verify(lectureRepository).findRegisteredLecturesByStudentId(1L);
    }

    @Test
    @DisplayName("특강 신청 성공")
    void registerLectureSuccess() {
        LectureRequestDto lectureRequestDto = new LectureRequestDto(1L, 1L, 1L);
        LectureRegistrationDto expectedLectureDto = LectureRegistrationDto.builder()
                .id(1L)
                .studentId(1L)
                .lectureId(1L)
                .lectureScheduleId(1L)
                .registrationDate(LocalDateTime.now())
                .build();

        when(lectureRepository.registerLecture(lectureRequestDto)).thenReturn(expectedLectureDto);

        LectureRegistrationDto resultLectureDto = lectureService.registerLecture(lectureRequestDto);

        assertEquals(expectedLectureDto, resultLectureDto);
    }

    @Test
    @DisplayName("특정 특강 스케줄의 잔여석 감소 성공")
    void decrementRemainCapacitySuccess() {
        lectureService.decrementRemainCapacity(1L);
        verify(lectureRepository).decrementRemainCapacity(1L);
    }
}
