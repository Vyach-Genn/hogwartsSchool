package prosky.ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prosky.ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    Optional<Student> findById(Long studentId);

    @Query("SELECT COUNT(student) FROM Student student")
    Long findCountAllStudents();

    @Query("    SELECT AVG(student.age) FROM Student student")
    Double findAverageAgeOfAllStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findTop5ByOrderByIdDesc();

}
