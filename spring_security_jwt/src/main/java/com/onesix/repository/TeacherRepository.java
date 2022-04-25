package com.onesix.repository;

import com.onesix.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByTeacherName(String username);
}
