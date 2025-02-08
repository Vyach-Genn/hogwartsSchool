package prosky.ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prosky.ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {

    private FacultyService out;
    private Map<Long, Faculty> facultyMap;

    @BeforeEach
    void setUp() {
        facultyMap = new HashMap<>(ContansForTest.FACULTY_MAP);
        out = new FacultyService(facultyMap);
    }

    @Test
    void createFaculty() {
        Faculty newFaculty = new Faculty(null, "Faculty 4", "Blue");

        Faculty createdFaculty = out.createFaculty(newFaculty);

        assertNotNull(createdFaculty);
        assertEquals("Faculty 4", (createdFaculty.getName()));
    }

    @Test
    void getFacultyById() {
        Faculty getedFaculty = out.getFacultyById(2L);

        assertEquals(facultyMap.get(2L).getName(), getedFaculty.getName());

    }

    @Test
    void updateFaculty() {
        Faculty updatedFaculty = new Faculty(2L, "Faculty", "Blue");

        Faculty realFaculty = out.updateFaculty(updatedFaculty);

        assertNotNull(realFaculty);
        assertEquals("Faculty", realFaculty.getName());
    }

    @Test
    void deleteFaculty() {
        out.deleteFaculty(1L);

        assertNull(out.getFacultyById(1L));
    }

    @Test
    void getAllFacultyByAge() {
        Collection<Faculty> facultys = out.getFacultyByColor("Blue");

        assertNotNull(facultys);
        assertEquals(1, facultys.size());
        assertEquals("Slytherin", facultys.iterator().next().getName());
    }

}