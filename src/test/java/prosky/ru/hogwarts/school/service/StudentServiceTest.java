package prosky.ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prosky.ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService out;
    private Map<Long, Student> studentMap;

    @BeforeEach
    void setUp() {
        studentMap = new HashMap<>(ContansForTest.STUDENT_MAP);
        out = new StudentService(studentMap);
    }

    @Test
    void createStudent() {
        Student newStudent = new Student(null, "Student 4", 14);

        Student createdStudent = out.createStudent(newStudent);

        assertNotNull(createdStudent);
        assertEquals("Student 4", (createdStudent.getName()));
    }

    @Test
    void getStudentById() {
        Student getedStudent = out.getStudentById(2L);

        assertEquals(studentMap.get(2L).getName(), getedStudent.getName());

    }

    @Test
    void updateStudent() {
        Student updatedStudent = new Student(2L, "Student", 20);

        Student realStudent = out.updateStudent(updatedStudent);

        assertNotNull(realStudent);
        assertEquals("Student", realStudent.getName());
    }

    @Test
    void deleteStudent() {
        out.deleteStudent(1L);

        assertNull(out.getStudentById(1L));
    }

    @Test
    void getAllStudentByAge() {
        Collection<Student> students = out.getAllStudentByAge(12L);

        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals("Harry", students.iterator().next().getName());
    }
}