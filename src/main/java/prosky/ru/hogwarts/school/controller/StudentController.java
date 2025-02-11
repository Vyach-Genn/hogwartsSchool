package prosky.ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RequestMapping("student")
@RestController
@AllArgsConstructor
public class StudentController {


    private final StudentService studentServices;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentServices.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentServices.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping(value = "/age/{age}")
    public ResponseEntity<Collection<Student>> getAllStudentByAge(@PathVariable int age) {
        return ResponseEntity.ok(studentServices.findStudentByAge(age));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentServices.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping(value = "{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentServices.checkStudentExists(studentId);
        studentServices.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
