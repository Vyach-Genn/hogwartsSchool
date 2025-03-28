package prosky.ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@RequestMapping("faculty")
@RestController
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping()
    public Collection<Faculty> getFaculty(@RequestParam(required = false) Long facultyId,
                                          @RequestParam(required = false) String color,
                                          @RequestParam(required = false) String name) {
        if (facultyId != null) {
            Faculty faculty = facultyService.getFacultyById(facultyId);
            return Collections.singletonList(faculty);
        }

        if (color != null && !color.isBlank()) {
            return facultyService.findByColor(color);
        }
        if (name != null && !name.isBlank()) {
            Faculty faculty = facultyService.findByName(name);
            return Collections.singletonList(faculty);
        }
        return facultyService.getAllFaculty();
    }

    @GetMapping("{facultyId}/students")
    public Set<Student> getStudentByFaculty(@PathVariable Long facultyId) {
        return facultyService.getStudentsByFaculty(facultyId);
    }

    @PutMapping(value = "{facultyId}")
    public Faculty PupdateFaculty(@PathVariable Long facultyId,
                                  @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(facultyId, faculty);
    }

    @DeleteMapping("{facultyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
    }

    @GetMapping(value = "/londName")
    public String getTheLongestFacultyName() {
        return facultyService.getTheLongestFacultyName();
    }
}
