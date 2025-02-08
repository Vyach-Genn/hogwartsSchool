package prosky.ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import prosky.ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private Map<Long, Student> studentMap = new HashMap<>();
    private Long generatedUserId = 0L;

    public StudentService(Map<Long, Student> studentMap) {
        this.studentMap = new HashMap<>(studentMap);
        this.generatedUserId = studentMap.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }

    public Student createStudent(Student student) {
        student.setId(generatedUserId++);
        studentMap.put(generatedUserId, student);
        return student;
    }

    public Student getStudentById(Long studentId) {
        return studentMap.get(studentId);
    }

    public Student updateStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public void deleteStudent(Long studentId) {
        studentMap.remove(studentId);
    }

    public Collection<Student> getAllStudentByAge(Long studentAge) {
        return studentMap.values().stream()
                .filter(student -> student.getAge() == studentAge)
                .collect(Collectors.toList());
    }
}

