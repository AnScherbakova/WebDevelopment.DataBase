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
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
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

    @GetMapping("/allStudentsUpperCase")
    public List<Student> getAllStudentsUpCase() {
        return studentService.getAllStudentsUpCase();
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getOnAge(int age) {

        if (age != 0) {
            return ResponseEntity.ok(studentService.getOnAge(age));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ageBetween")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam int min,
                                                                       @RequestParam int max) {
        return ResponseEntity.ok(studentService.getByAgeBetween(min, max));
    }

    @GetMapping("/{id}/faculties")
    public Faculty getFacultyByStudent(@PathVariable long id) {
        return studentService.getFacultyByStudent(id);
    }

    @GetMapping("/integerValue")
    public Integer getIntegerValue() {
        return studentService.getIntegerValue();
    }

    @GetMapping("/thread")
    public void listStudents() {
        studentService.listStudentsName();
    }

    @GetMapping("/threadSynchronized")
    public void listStudentsSynchronized() {
        studentService.listStudentsSynchronized();
    }

}
