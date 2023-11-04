package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public final Object flag = new Object();
    int count = 0;

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) // конструктор
    {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) // метод добавления студентов
    {
        logger.info("Был вызван метод для создания студента: {} ", student);
        return studentRepository.save(student);
    }

    public Student findStudent(long id) //метод получения студентов по id
    {
        logger.info("Был вызван метод для получения студента по id: {} ", id);
        return studentRepository.findById(id).get();
    }

    public Student editStudent(long id, Student student) //метод редактирования студентов
    {
        logger.debug("Был вызван метод для редактирования студента: {} ", student);
        if (studentRepository.existsById(student.getId())) {
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(long id) // метод удаления студентов
    {
        logger.debug("Был вызван метод для удаления студента по id: {} ", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() //метод получения всех студентов
    {
        logger.info("Был вызван метод получения всех студентов без параметров");
        return studentRepository.findAll();
    }

    public Collection<Student> getOnAge(int age) //метод получения студентов по возрасту
    {
        logger.info("Был вызван метод получения студентов по возрасту: {} ", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getByAgeBetween(int min, int max) //метод получения студентов по заданным параметрам мин/макс
    {
        logger.info("Был вызван метод получения студентов по возрасту from {} to {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFacultyByStudent(long studentId) //метод получения факультета по студенту
    {
        logger.info("Был вызван метод получения факультета по студенту: {} ", studentId);
        return studentRepository.findById(studentId).map(Student::getFaculty).orElse(null);
    }

    public double getAverageAge() {
        logger.info("Был вызван метод среднего возраста всех студентов");
        return studentRepository.getAverageAge();
    }

    public Integer getAllStud() {
        logger.info("Был вызван метод получения числа всех студентов");
        return studentRepository.getAllStudents();
    }

    public Collection<Student> getLastStudents() {
        logger.info("Был вызван метод получения последних пяти студентов");
        return studentRepository.getLastStudent();
    }


    public List<Student> getAllStudentsUpCase() {
        logger.info("Был вызван метод получения всех студентов, чье имя начинается с большой буквы А");
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getName().toLowerCase().toUpperCase().startsWith("А"))
                .collect(Collectors.toList());
    }


    public Integer getIntegerValue() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, i -> i + 1)
//                .parallel()             //38ms
                .limit(1_000_000)
//                .reduce(0, (a, b) -> a + b); //26ms
                .mapToInt(value -> value)
                .sum();  //18ms
        long integerValue = System.currentTimeMillis() - start;
        logger.info("Время выполнения: " + integerValue + "ms");
        return sum;
    }

    public void listStudentsName () {
        List<String> listStudents = getListStudents();
        for (int i = 0; i < 2; i++) {
            System.out.println(listStudents.get(i));
        }

        new Thread(() -> {
            for (int i = 2; i < 4; i++) {
                System.out.println(listStudents.get(i));
            }
        }).start();

        new Thread(() -> {
            for (int i = 4; i < 6; i++) {
                System.out.println(listStudents.get(i));
            }
        }).start();

        new Thread(() -> {
            for (int i = 6; i < 8; i++) {
                System.out.println(listStudents.get(i));
            }
        }).start();
    }

    public void listStudentsSynchronized() {
        List<String> listStudents = getListStudents();

        printStudentName(listStudents.get(0));
        printStudentName(listStudents.get(1));

        Thread threadOne = new Thread(() -> {
            printStudentName(listStudents.get(2));
            printStudentName(listStudents.get(3));
        });

        Thread threadTwo = new Thread(() -> {
            printStudentName(listStudents.get(4));
            printStudentName(listStudents.get(5));
        });

        threadOne.start();
        threadTwo.start();
    }

    private List <String> getListStudents () {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public void printStudentName (String student) {
        synchronized (flag) {
            System.out.println(student + ": " + count);
            count++;
        }
    }


}
