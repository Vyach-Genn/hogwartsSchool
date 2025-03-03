package prosky.ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prosky.ru.hogwarts.school.exception.NotFoundException;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static prosky.ru.hogwarts.school.service.ContansForTest.*;

class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService out;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createFaculty_shouldReturnFacultyWithId() {
        when(facultyRepository.save(GRYFFINDOR)).thenReturn(GRYFFINDOR);

        Faculty actual = out.createFaculty(GRYFFINDOR);

        assertNotNull(actual);
        assertEquals("Gryffindor", actual.getName());
        verify(facultyRepository, times(1)).save(GRYFFINDOR);
    }

    @Test
    void getFacultyById_shouldReturnFaculty() {
        when(facultyRepository.existsById(1L)).thenReturn(true);
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(GRYFFINDOR));

        Faculty actual = out.getFacultyById(1L);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
        assertEquals("Gryffindor", actual.getName());
        verify(facultyRepository, times(1)).findById(1L);
        verify(facultyRepository, times(1)).existsById(1L);

    }

    @Test
    void getFacultyById_ThrowsExceptionIfFacultyNotFound() {
        when(facultyRepository.existsById(10000L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> out.getFacultyById(10000L));

        verify(facultyRepository, times(1)).existsById(10000L);
    }

    @Test
    void updateFaculty() {
        when(facultyRepository.existsById(1L)).thenReturn(true);
        when(facultyRepository.save(SLYTHERIN)).thenReturn(SLYTHERIN);

        Faculty actual = out.updateFaculty(1L, SLYTHERIN);

        assertNotNull(actual);
        assertEquals("Slytherin", actual.getName());
        verify(facultyRepository, times(1)).existsById(1L);
        verify(facultyRepository, times(1)).save(SLYTHERIN);
    }

    @Test
    void updateFaculty_shouldThrowNotFoundException() {
        when(facultyRepository.existsById(999L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> out.updateFaculty(999L, GRYFFINDOR));
        verify(facultyRepository, times(1)).existsById(999L);
    }

    @Test
    void deleteFaculty() {
        when(facultyRepository.existsById(1L)).thenReturn(true);

        out.deleteFaculty(1L);

        verify(facultyRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteFaculty_ThrowsExceptionIfFacultyNotFound() {
        when(facultyRepository.existsById(999L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> out.deleteFaculty(999L));
        verify(facultyRepository, times(1)).existsById(999L);
    }

    @Test
    void getAllFacultyByColor() {
        when(facultyRepository.findByColorIgnoreCase("Blue")).thenReturn(List.of(SLYTHERIN));

        Collection<Faculty> facultys = out.findByColor("Blue");

        assertNotNull(facultys);
        assertEquals(1, facultys.size());
        assertEquals("Slytherin", facultys.iterator().next().getName());
        assertTrue(facultys.stream().allMatch(f -> f.getColor().equals("Blue")));
    }

    @Test
    void GetStudentsByFaculty_shouldReturnStudentByFaculty() {
        Long facultyId = 1L;
        when(facultyRepository.findStudentsByFacultyId(facultyId)).thenReturn(GRYFFINDOR_SET_STUDENT);

        Set<Student> actual = out.getStudentsByFaculty(facultyId);

        assertEquals(GRYFFINDOR_SET_STUDENT, actual);
        verify(facultyRepository, times(1)).findStudentsByFacultyId(facultyId);
    }

}