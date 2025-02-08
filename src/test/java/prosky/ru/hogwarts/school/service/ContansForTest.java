package prosky.ru.hogwarts.school.service;

import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;

import java.util.Map;

public class ContansForTest {

    public static final Map<Long, Student> STUDENT_MAP = Map.of(
            1L, new Student(1L, "Harry", 12),
            2L, new Student(2L, "Ron", 11),
            3L, new Student(3L, "Hermione", 10)
    );

    public static final Map<Long, Faculty> FACULTY_MAP = Map.of(
            1L, new Faculty(1L, "Gryffindor", "Red"),
            2L, new Faculty(2L, "Hufflepuff", "Yellow"),
            3L, new Faculty(3L, "Slytherin", "Blue")
    );
}
