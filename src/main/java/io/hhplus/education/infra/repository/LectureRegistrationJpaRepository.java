package io.hhplus.education.infra.repository;

import io.hhplus.education.infra.entity.LectureRegistration;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface LectureRegistrationJpaRepository extends JpaRepository<LectureRegistration, Long> {

    List<LectureRegistration> findByStudentId(Long studentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureRegistration> findByLectureScheduleIdAndStudentId(Long lectureScheduleId, Long studentId);
}
