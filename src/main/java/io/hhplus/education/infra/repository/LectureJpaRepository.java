package io.hhplus.education.infra.repository;

import io.hhplus.education.infra.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}
