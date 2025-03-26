package prosky.ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RequestMapping("student")
@RestController
@AllArgsConstructor
public class StudentController {


    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping
    public ResponseEntity getStudent(@RequestParam(required = false) Long id,
                                     @RequestParam(required = false) Integer age,
                                     @RequestParam(required = false) Integer minAge,
                                     @RequestParam(required = false) Integer maxAge) {
        if (id != null) {
            return ResponseEntity.ok(studentService.getStudentById(id));
        }
        if (age != null) {
            return ResponseEntity.ok(studentService.findStudentByAge(age));
        }
        if (minAge != null && maxAge != null && minAge > 0 && maxAge > minAge) {
            return ResponseEntity.ok(studentService.findByMinAgeAndMaxAge(minAge, maxAge));
        }
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("{studentId}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable Long studentId) {
        Faculty faculty = studentService.getFacultyByStudentId(studentId);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping(value = "{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAllStudents() {
        return ResponseEntity.ok(studentService.getCountAllStudents());
    }

    @GetMapping("/average")
    public ResponseEntity<Double> getAverageAgeOfAllStudents() {
        return ResponseEntity.ok(studentService.getAverageAgeOfAllStudents());
    }

    @GetMapping("/limit_5")
    public ResponseEntity<Collection<Student>> getTop5ByOrderByIdDesc() {
        return ResponseEntity.ok(studentService.getTop5ByOrderById());
    }

    @GetMapping(value = "A")
    public ResponseEntity<Collection<String>> getStudentsNamesStartingWithA() {
        return ResponseEntity.ok(studentService.getStudentsNamesStartingWithA());
    }

    @GetMapping(value = "/ave")
    public ResponseEntity<Double> getAveAgeOfAllStudent() {
        return ResponseEntity.ok(studentService.getAveAgeOfAllStudents());
    }

    @GetMapping(value = "/print-parallel")
    public ResponseEntity<String> printStudentsParallel() {
        return ResponseEntity.ok(studentService.printStudentsParallel());
    }

    @GetMapping(value = "/print-synchronized")
    public ResponseEntity<String> printStudentSynchronizedParallel() {
        return ResponseEntity.ok(studentService.printStudentsSynchronizedParallel());
    }
}