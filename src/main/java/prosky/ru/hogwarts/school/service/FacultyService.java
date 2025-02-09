package prosky.ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.exception.NotFountException;
import prosky.ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long generatedUserId = 0L;


    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++generatedUserId);
        facultyMap.put(generatedUserId, faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        checkFacultyExists(id);
        return facultyMap.get(id);
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        checkFacultyExists(id);
        faculty.setId(id);
        facultyMap.put(id, faculty);
        return faculty;
    }

    public void deleteFaculty(Long facultyId) {
        checkFacultyExists(facultyId);
        facultyMap.remove(facultyId);
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyMap.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public void checkFacultyExists(Long id) {
        if (!facultyMap.containsKey(id)) {
            throw new NotFountException("Error: Факултет с id " + id + " не найден");
        }
    }
}
