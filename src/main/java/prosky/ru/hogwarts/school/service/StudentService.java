package prosky.ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.exception.NotFountException;
import prosky.ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Map<Long, Student> studentMap = new HashMap<>();
    private Long generatedUserId = 0L;


    public Student createStudent(Student student) {
        student.setId(++generatedUserId);
        studentMap.put(generatedUserId, student);
        return student;
    }

    public Student getStudentById(Long id) {
        checkStudentExists(id);
        return studentMap.get(id);
    }

    public Student updateStudent(Long id, Student student) {
        checkStudentExists(id);
        student.setId(id);
        studentMap.put(id, student);
        return student;
    }

    public void deleteStudent(Long studentId) {
        checkStudentExists(studentId);
        studentMap.remove(studentId);
    }

    public Collection<Student> getAllStudentByAge(Long studentAge) {
        return studentMap.values().stream()
                .filter(student -> student.getAge() == studentAge)
                .collect(Collectors.toList());
    }

    public void checkStudentExists(Long id) {
        if (!studentMap.containsKey(id)) {
            throw new NotFountException("Error: Студент с id " + id + " не найден");
        }
    }
}

