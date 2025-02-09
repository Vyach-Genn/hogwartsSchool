package prosky.ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RequestMapping("student")
@RestController
public class StudentController {


    private final StudentService studentServices;

    public StudentController(StudentService studentServices) {
        this.studentServices = studentServices;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentServices.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentServices.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping(value = "/age/{age}")
    public ResponseEntity<Collection<Student>> getAllStudentByAge(@PathVariable Long age) {
        if (age > 0) {
            return ResponseEntity.ok(studentServices.getAllStudentByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentServices.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping(value = "{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentServices.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
