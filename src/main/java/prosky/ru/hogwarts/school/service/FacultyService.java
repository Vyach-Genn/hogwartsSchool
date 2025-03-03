package prosky.ru.hogwarts.school.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.exception.NotFoundException;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Set;

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
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Faculty findByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Set<Student> getStudentsByFaculty(Long facultyId) {
        return facultyRepository.findStudentsByFacultyId(facultyId);

    }

    public void checkFacultyExists(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new NotFoundException("Error: Факультет с id " + id + " не найден");
        }
    }
}
