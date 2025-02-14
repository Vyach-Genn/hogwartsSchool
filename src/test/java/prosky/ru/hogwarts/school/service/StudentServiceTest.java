package prosky.ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prosky.ru.hogwarts.school.exception.NotFoundException;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static prosky.ru.hogwarts.school.service.ContansForTest.HARRY;
import static prosky.ru.hogwarts.school.service.ContansForTest.RON;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService out;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createStudent_shouldReturnStudentWithId() {
        when(studentRepository.save(HARRY)).thenReturn(HARRY);

        Student actual = out.createStudent(HARRY);

        assertNotNull(actual);
        assertEquals("Harry", actual.getName());
        verify(studentRepository, times(1)).save(HARRY);
    }

    @Test
    void getStudentById_shouldReturnStudent() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(HARRY));

        Student actual = out.getStudentById(1L);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
        assertEquals("Harry", actual.getName());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).existsById(1L);

    }

    @Test
    void getStudentById_ThrowsExceptionIfStudentNotFound() {
        when(studentRepository.existsById(10000L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> out.getStudentById(10000L));

        verify(studentRepository, times(1)).existsById(10000L);
    }

    @Test
    void updateStudent() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(RON)).thenReturn(RON);

        Student actual = out.updateStudent(1L, RON);

        assertNotNull(actual);
        assertEquals("Ron", actual.getName());
        verify(studentRepository, times(1)).existsById(1L);
        verify(studentRepository, times(1)).save(RON);
    }

    @Test
    void updateStudent_shouldThrowNotFoundException() {
        when(studentRepository.existsById(999L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> out.updateStudent(999L, HARRY));
        verify(studentRepository, times(1)).existsById(999L);
    }

    @Test
    void deleteStudent() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        out.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudent_ThrowsExceptionIfStudentNotFound() {
        when(studentRepository.existsById(999L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> out.deleteStudent(999L));
        verify(studentRepository, times(1)).existsById(999L);
    }

    @Test
    void getAllStudentByColor() {
        when(studentRepository.findByAge(12)).thenReturn(List.of(HARRY));

        Collection<Student> Students = out.findStudentByAge(12);

        assertNotNull(Students);
        assertEquals(1, Students.size());
        assertEquals("Harry", Students.iterator().next().getName());
        assertTrue(Students.stream().allMatch(f -> f.getAge() == 12));
    }
}