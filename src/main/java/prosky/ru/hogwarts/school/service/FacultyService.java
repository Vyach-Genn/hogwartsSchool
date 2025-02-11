package prosky.ru.hogwarts.school.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.exception.NotFountException;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class FacultyService {

    FacultyRepository facultyRepository;


    public Faculty createFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        checkFacultyExists(id);
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        checkFacultyExists(id);
        faculty.setId(id);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        checkFacultyExists(facultyId);
        facultyRepository.deleteById(facultyId);
    }

    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public void checkFacultyExists(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new NotFountException("Error: Факультет с id " + id + " не найден");
        }
    }
}
