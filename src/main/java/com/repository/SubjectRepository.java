package com.repository;

import com.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject getSubjectBySubjectCode(String subjectCode);
}
