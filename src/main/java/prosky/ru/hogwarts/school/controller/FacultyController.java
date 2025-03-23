package prosky.ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.repository.FacultyRepository;
import prosky.ru.hogwarts.school.service.FacultyService;

import java.util.Set;

@RequestMapping("faculty")
@RestController
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping()
    public ResponseEntity getFaculty(@RequestParam(required = false) Long facultyId,
                                     @RequestParam(required = false) String color,
                                     @RequestParam(required = false) String name) {
        if (facultyId != null) {
            return ResponseEntity.ok(facultyService.getFacultyById(facultyId));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.findByName(name));
        }
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("{facultyId}/students")
    public ResponseEntity<Set<Student>> getStudentByFaculty(@PathVariable Long facultyId) {
        Set<Student> students = facultyService.getStudentsByFaculty(facultyId);
        return ResponseEntity.ok(students);
    }

    @PutMapping(value = "{facultyId}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long facultyId, @RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(facultyId, faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/londName")
    public ResponseEntity<String> getTheLongestFacultyName() {
        return ResponseEntity.ok(facultyService.getTheLongestFacultyName());
    }


}
