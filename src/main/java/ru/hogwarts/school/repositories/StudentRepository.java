package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT AVG (age) AS averageAge FROM student", nativeQuery = true)
    public Double getAverageAge();

    @Query(value = "SELECT COUNT (id) FROM student", nativeQuery = true)
    public Integer getAllStudents();

    @Query(value = "SELECT*FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLastStudent();

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);


}
