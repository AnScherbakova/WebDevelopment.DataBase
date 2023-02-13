package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/allStudents")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getOnAge(@RequestParam (required = false) String name, long age) {

        if (age != 0) {
            return ResponseEntity.ok(studentService.getOnAge(name, age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


    @PostMapping("/createStudents")
    public Student createStudent(@RequestBody Student student) {

        return studentService.createStudent(student);
    }

    @PutMapping("/editStudents")
    public ResponseEntity<Student> editStudent(@RequestBody Student student, long id) {
        Student foundStudent = studentService.editStudent(id, student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ageBetween")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam long min,
                                                                       @RequestParam long max) {
        return ResponseEntity.ok(studentService.getByAgeBetween(min, max));
    }

    @GetMapping("{id}/facultyStudent")
    public Faculty getFacultyStudent (@PathVariable long id) {
        return studentService.getFacultyStudent(id);
    }


}
