package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) //конструктор
    {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) //метод добавления факультета
    {
        logger.info ("Был вызван метод для создания факультета: {} ", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) //метод получения факультета по id
    {
        logger.info ("Был вызван метод для получения факультета по id: {} ", id);
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> getAllFaculties() //все факультеты
    {
        logger.info ("Был вызван метод для получения всех факультетов без заданных параметров");
        return facultyRepository.findAll();
    }

    public Faculty editFaculty(long id, Faculty faculty) //метод редактирования факультета
    {
        logger.debug ("Был вызван метод для редактирования факультета: {} ", faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) //метод удаления факультета
    {
        logger.debug ("Был вызван метод для удаления факультета по id: {} ", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getOnColor(String color) //метод получения факультета по цвету
    {
        logger.info ("Был вызван метод для получения факультета по цвету: {} ", color);
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    public Collection<Faculty> getByColor( String color) //метод получения факультета по цвету
    {
        return facultyRepository.findByColorIgnoreCase (color);
    }

    public Collection<Faculty> getByName(String name) //метод получения факультета по названию
    {
        logger.info ("Был вызван метод для получения факультета по названию: {} ", name);
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Student> getStudentsByFaculty(long facultyId) {
        logger.info ("Был вызван метод для получения всех студентов по одному факультету: {} ", facultyId);
       return facultyRepository.findById(facultyId).get().getStudents();
    }


    public String getLongNameFaculty() {
        return facultyRepository.findAll()
                .stream()
                .max(Comparator.comparing(s -> s.getName().length()))
                .get()
                .getName();
    }
}
