package prosky.ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prosky.ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColor(String color);

}
