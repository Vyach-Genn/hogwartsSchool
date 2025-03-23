package prosky.ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.exception.NotFoundException;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Set;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        facultyRepository.save(faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        logger.info("The method was invoked to get a faculty by ID");
        checkFacultyExists(id);
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        logger.info("A method was invoked to change a faculty by its ID");
        checkFacultyExists(id);
        faculty.setId(id);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long facultyId) {
        logger.info("The method to delete a faculty by ID was invoked");
        checkFacultyExists(facultyId);
        facultyRepository.deleteById(facultyId);
    }

    public List<Faculty> findByColor(String color) {
        logger.info("The faculty search method by color was called");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Faculty findByName(String name) {
        logger.info("The faculty search method by name was called");
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public List<Faculty> getAllFaculty() {
        logger.info("The show all faculties method was called");
        return facultyRepository.findAll();
    }

    public Set<Student> getStudentsByFaculty(Long facultyId) {
        logger.info("the method to show the faculty of a specific student was called");
        return facultyRepository.findStudentsByFacultyId(facultyId);

    }

    public void checkFacultyExists(Long id) {
        logger.error("There is not student with id = {}", id);
        if (!facultyRepository.existsById(id)) {
            throw new NotFoundException("Error: Факультет с id " + id + " не найден");
        }
    }
}
