package com.onesix.controller;

import com.onesix.entity.AuthenticationRequest;
import com.onesix.entity.Teacher;
import com.onesix.security.JwtTokenGenerator;
import com.onesix.security.MyUserDetailsService;
import com.onesix.service.TeacherService;
import com.onesix.util.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/teachers")
    public ResponseEntity<Object> saveTeacher(@RequestBody Teacher teacher) {
        return ResponseFormat.generateResponse("Data signed Successfully", HttpStatus.OK.value(), teacherService.saveTeacher(teacher));
    }

    @GetMapping("/teachers")
    public ResponseEntity<Object> getAllTeachers() {
        return ResponseFormat.generateResponse("users retrieved successfully", HttpStatus.OK.value(), teacherService.getAllTeacher());
    }

    @PostMapping("/authenticate")
    public Object authenticateTeacher(@RequestBody AuthenticationRequest ar) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(ar.getUsername(), ar.getPassword()));

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(ar.getUsername());
        String token = jwtTokenGenerator.generateToken(userDetails);

        System.out.println(token);

        return ResponseFormat.generateResponse("token created successfully",
                HttpStatus.OK.value(), token);

    }
}
