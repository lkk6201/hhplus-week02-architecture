package io.hhplus.education.infra.repository;

import io.hhplus.education.infra.entity.LectureRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRegistrationJpaRepository extends JpaRepository<LectureRegistration, Long> {

    List<LectureRegistration> findByStudentId(Long studentId);

    Optional<LectureRegistration> findByLectureScheduleIdAndStudentId(Long lectureScheduleId, Long studentId);
}
