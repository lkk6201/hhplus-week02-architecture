package io.hhplus.education.infra.repository;

import io.hhplus.education.infra.entity.LectureSchedule;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureScheduleJpaRepository extends JpaRepository<LectureSchedule, Long> {
    @Query("SELECT ls FROM LectureSchedule ls " +
            "LEFT JOIN LectureRegistration lr ON ls.id = lr.lectureScheduleId AND lr.studentId = :studentId " +
            "WHERE ls.expireDate > :targetDate AND ls.remainCapacity > 0 AND lr.studentId IS NULL")
    List<LectureSchedule> findAvailableLecturesByStudentId(@Param("targetDate") LocalDateTime targetDate,
                                                           @Param("studentId")Long studentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureSchedule> findById(Long id);
}
