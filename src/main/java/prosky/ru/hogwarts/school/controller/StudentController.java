package prosky.ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RequestMapping("student")
@RestController
@AllArgsConstructor
public class StudentController {


    private final StudentService studentService;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping
    public Collection<Student> getStudent(@RequestParam(required = false) Long id,
                                     @RequestParam(required = false) Integer age,
                                     @RequestParam(required = false) Integer minAge,
                                     @RequestParam(required = false) Integer maxAge) {
        if (id != null) {
            Student student = studentService.getStudentById(id);
            return Collections.singletonList(student);
        }
        if (age != null) {
            return studentService.findStudentByAge(age);
        }
        if (minAge != null && maxAge != null && minAge > 0 && maxAge > minAge) {
            return studentService.findByMinAgeAndMaxAge(minAge, maxAge);
        }
        return studentService.getAllStudent();
    }

    @GetMapping("{studentId}/faculty")
    public Faculty getFacultyByStudentId(@PathVariable Long studentId) {
        return studentService.getFacultyByStudentId(studentId);
    }

    @PutMapping(value = "{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @GetMapping("/count")
    public Long countAllStudents() {
        return studentService.getCountAllStudents();
    }

    @GetMapping("/average")
    public Double getAverageAgeOfAllStudents() {
        return studentService.getAverageAgeOfAllStudents();
    }

    @GetMapping("/limit_5")
    public Collection<Student> getTop5ByOrderByIdDesc() {
        return studentService.getTop5ByOrderById();
    }

    @GetMapping(value = "A")
    public Collection<String> getStudentsNamesStartingWithA() {
        return studentService.getStudentsNamesStartingWithA();
    }

    @GetMapping(value = "/ave")
    public Double getAveAgeOfAllStudent() {
        return studentService.getAveAgeOfAllStudents();
    }

    @GetMapping(value = "/print-parallel")
    public String printStudentsParallel() {
        return studentService.printStudentsParallel();
    }

    @GetMapping(value = "/print-synchronized")
    public String printStudentSynchronizedParallel() {
        return studentService.printStudentsSynchronizedParallel();
    }
}