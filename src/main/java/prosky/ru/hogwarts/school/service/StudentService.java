package prosky.ru.hogwarts.school.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.exception.NotFoundException;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Student createStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student getStudentById(Long id) {
        checkStudentExists(id);
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Long id, Student student) {
        checkStudentExists(id);
        student.setId(id);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        checkStudentExists(studentId);
        studentRepository.deleteById(studentId);
    }

    public Collection<Student> findStudentByAge(int studentAge) {
        return studentRepository.findByAge(studentAge);
    }

    public Collection<Student> findByMinAgeAndMaxAge(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Faculty getFacultyByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Студент с id " + studentId + " не найден"));

        Faculty faculty = student.getFaculty();
        faculty.setStudentName(student.getName());

        return faculty;
    }

    public Long getCountAllStudents() {
        return studentRepository.findCountAllStudents();
    }

    public Double getAverageAgeOfAllStudents() {
        return studentRepository.findAverageAgeOfAllStudents();
    }

    public Collection<Student> getTop5ByOrderById() {
        return studentRepository.findTop5ByOrderByIdDesc();
    }

    public void checkStudentExists(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Error: Студент с id " + id + " не найден");
        }
    }
}

