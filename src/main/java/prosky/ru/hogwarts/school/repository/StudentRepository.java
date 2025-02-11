package prosky.ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prosky.ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);
}
