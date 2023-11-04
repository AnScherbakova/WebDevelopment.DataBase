package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.parameters.PathParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/color")
    public ResponseEntity<Collection<Faculty>> getOnColor(String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.getOnColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/allFaculties")
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/longNameFaculty")
    public String getLongNameFaculty() {
        return facultyService.getLongNameFaculty();
    }


    @GetMapping("/nameOrColor")
    public ResponseEntity<Collection<Faculty>> getByColorOrName(@RequestParam(required = false) String name,
                                                                @RequestParam(required = false) String color) {

        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.getByName(name));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.getByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


    @PostMapping("/createFaculties")
    public Faculty createFaculty(@RequestBody Faculty faculty) {

        return facultyService.createFaculty(faculty);
    }

    @PutMapping("/editFaculties")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty, long id) {
        Faculty foundFaculty = facultyService.editFaculty(id, faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/allStudentsFaculty")
    public Collection<Student> getStudentsFaculty(@PathVariable long id) {
        return facultyService.getStudentsByFaculty(id);
    }
}
