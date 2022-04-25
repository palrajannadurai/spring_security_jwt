package com.onesix.service;

import com.onesix.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher saveTeacher(Teacher teacher);

    List<Teacher> getAllTeacher();
}
