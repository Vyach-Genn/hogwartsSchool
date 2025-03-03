package prosky.ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColorIgnoreCase(String color);

    Faculty findByNameIgnoreCase(String name);

    @Query("SELECT s FROM Student s WHERE s.faculty.id = :facultyId")
    Set<Student> findStudentsByFacultyId(@Param("facultyId") Long facultyId);
}
