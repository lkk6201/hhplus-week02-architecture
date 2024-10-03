package io.hhplus.education.application.facade;

import io.hhplus.education.infra.entity.Lecture;
import io.hhplus.education.infra.entity.LectureRegistration;
import io.hhplus.education.infra.entity.LectureSchedule;
import io.hhplus.education.infra.repository.LectureJpaRepository;
import io.hhplus.education.infra.repository.LectureScheduleJpaRepository;
import io.hhplus.education.presentation.dto.LectureRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LectureFacadeTest {

    @Autowired
    private LectureFacade lectureFacade;

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private LectureScheduleJpaRepository lectureScheduleJpaRepository;

    private Lecture lecture;

    private LectureSchedule lectureSchedule;

    @BeforeEach
    void setUp() {
        lecture = Lecture.builder()
                .name("스프링 초급")
                .teacherName("이광규")
                .build();

        lectureJpaRepository.save(lecture);

        lectureSchedule = LectureSchedule.builder()
                .lectureId(lecture.getId())
                .maxCapacity(30L)
                .remainCapacity(30L)
                .expireDate(LocalDateTime.now())
                .build();

        lectureScheduleJpaRepository.save(lectureSchedule);
    }

    @Test
    @DisplayName("수강 신청 인원 초과 동시성 테스트")
    public void registerFailsIfMaxCapacityReached() throws Exception {
        int concurrentRequests = 40;

        ExecutorService executorService = Executors.newFixedThreadPool(concurrentRequests);
        List<Callable<Boolean>> tasks = new ArrayList<>();

        for (int i = 0; i < concurrentRequests; i++) {
            Long testUserId = (long) i;
            tasks.add(() -> {
                try {
                    LectureRequestDto requestDto = new LectureRequestDto(lecture.getId(), lectureSchedule.getId(), testUserId);
                    lectureFacade.registerLecture(requestDto);
                    return true;
                } catch (RuntimeException e) {
                    return false;
                }
            });
        }

        List<Future<Boolean>> futures = executorService.invokeAll(tasks);
        executorService.shutdown();

        int successfulRegistrations = 0;
        for (Future<Boolean> future : futures) {
            if (future.get()) {
                successfulRegistrations++;
            }
        }

        assertEquals(30, successfulRegistrations);
    }

    @Test
    @DisplayName("같은 사용자가 동일 특강에 대해 1번만 신청 성공하는 테스트")
    void allowSingleRegistrationPerUser() {

        int tryCount = 5;
        int successCount = 0;
        int failCount = 0;

        for (int i = 0; i < tryCount; i++) {
            try {
                LectureRequestDto requestDto = new LectureRequestDto(lecture.getId(), lectureSchedule.getId(), 1L);
                lectureFacade.registerLecture(requestDto);
                successCount++;
            } catch (RuntimeException e) {
                failCount++;
            }
        }

        assertEquals(1, successCount);
        assertEquals(4, failCount);
    }
}

