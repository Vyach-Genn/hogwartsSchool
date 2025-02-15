package prosky.ru.hogwarts.school.service;

import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;

import java.util.Set;

public class ContansForTest {

    public static final Student HARRY = new Student(1L, "Harry", 15);
    public static final Student RON = new Student(2L, "Ron", 11);

    public static final Faculty GRYFFINDOR = new Faculty(1L, "Gryffindor", "Red");
    public static final Faculty SLYTHERIN = new Faculty(2L, "Slytherin", "Blue");

    static{
        HARRY.setFaculty(GRYFFINDOR);
        RON.setFaculty(GRYFFINDOR);
    }

    public static final Set<Student> GRYFFINDOR_SET_STUDENT = Set.of(HARRY, RON);

}
