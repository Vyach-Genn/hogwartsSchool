package prosky.ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long generatedUserId = 1L;

    public FacultyService(Map<Long, Faculty> facultyMapMap) {
        this.facultyMap = new HashMap<>(facultyMapMap);
        this.generatedUserId = facultyMapMap.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(generatedUserId);
        facultyMap.put(generatedUserId, faculty);
        generatedUserId++;
        return faculty;
    }

    public Faculty getFacultyById(Long facultyId) {
        return facultyMap.get(facultyId);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public void deleteFaculty(Long facultyId) {
        facultyMap.remove(facultyId);
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyMap.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
