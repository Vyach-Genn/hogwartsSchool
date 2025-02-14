package prosky.ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prosky.ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    Optional<Student> findById(Long studentId);

}
