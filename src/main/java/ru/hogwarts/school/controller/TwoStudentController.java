package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
public class TwoStudentController {

    private final StudentService studentService;

    public TwoStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/averageAge")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/allStudents")
    public Integer getAllStudents() {
        return studentService.getAllStud();
    }

    @GetMapping("/lastStudents")
    public Collection<Student> getLastStudents() {
        return studentService.getLastStudents();
    }
}
