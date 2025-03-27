package prosky.ru.hogwarts.school.webmvc;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import prosky.ru.hogwarts.school.controller.StudentController;
import prosky.ru.hogwarts.school.model.Student;
import prosky.ru.hogwarts.school.repository.StudentRepository;
import prosky.ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class SchoolHogwartsStudentWebMvcApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @Test
    public void postStudentTest() throws Exception {
        Long id = 1L;
        String name = "Bob";
        int age = 20;

        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("name", name);
        userObject.put("age", age);

        Student student = new Student(id, name, age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        Long id = 1L;
        String name = "Bob";
        int age = 20;

        Student student = new Student();

        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.existsById(id)).thenReturn(true);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/student")
                        .param("id", id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentByIdTest_NotFound() throws Exception {
        Long id = 999L;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/student")
                        .param("id", id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStudentTest() throws Exception {
        Long id = 1L;
        String updatedName = "Updated Bob";
        int updatedAge = 25;

        JSONObject updatedUserObject = new JSONObject();
        updatedUserObject.put("id", id);
        updatedUserObject.put("name", updatedName);
        updatedUserObject.put("age", updatedAge);

        Student updatedStudent = new Student(id, updatedName, updatedAge);

        when(studentRepository.existsById(id)).thenReturn(true);
        when(studentRepository.findById(id)).thenReturn(Optional.of(updatedStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/{id}", id)
                        .content(updatedUserObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(updatedName))
                .andExpect(jsonPath("$.age").value(updatedAge));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Long id = 1L;

        when(studentRepository.existsById(id)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        Student student1 = new Student(1L, "Bob", 20);
        Student student2 = new Student(2L, "Alice", 22);

        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        mockMvc.perform(get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Alice"))
                .andExpect(jsonPath("$[1].age").value(22));
    }

    @Test
    void testCountAllStudents() throws Exception {
        when(studentService.getCountAllStudents()).thenReturn(20L);

        mockMvc.perform(get("/student/count")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(20L));
    }

    @Test
    void testgetAverageAgeOfAllStudents() throws Exception {
        when(studentService.getAverageAgeOfAllStudents()).thenReturn(9.0);

        mockMvc.perform(get("/student/average")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(9.0));
    }

    @Test
    void testGetTop5ByOrderByIdDesc() throws Exception {
        List<Student> students = List.of(
                new Student(1L, "Harry Potter", 11),
                new Student(2L, "Hermione Granger", 11),
                new Student(3L, "Ron Weasley", 11),
                new Student(4L, "Draco Malfoy", 11),
                new Student(5L, "Neville Longbottom", 11)
        );

        when(studentService.getTop5ByOrderById()).thenReturn(students);

        mockMvc.perform(get("/student/limit_5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Harry Potter"))
                .andExpect(jsonPath("$[1].name").value("Hermione Granger"))
                .andExpect(jsonPath("$[4].name").value("Neville Longbottom"));
    }

    @Test
    void testPrintStudentsParallel() throws Exception {
        when(studentService.printStudentsParallel()).thenReturn("Запущены параллельные потоки для вывода студентов");

        mockMvc.perform(get("/student/print-parallel"))
                .andExpect(status().isOk())
                .andExpect(content().string("Запущены параллельные потоки для вывода студентов"));
    }

    @Test
    void printParallel_ShouldReturnBadRequest_WhenNotEnoughStudents() throws Exception {
        when(studentRepository.find6StudentByOrder()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/student/print-parallel"))
                .andExpect(content().string(containsString("Недостаточно студентов")));
    }

    @Test
    void testPrintStudentsSynchronizedParallel() throws Exception {
        when(studentService.printStudentsSynchronizedParallel()).thenReturn("Запущен синхронизированный вывод студентов");

        mockMvc.perform(get("/student/print-synchronized"))
                .andExpect(status().isOk())
                .andExpect(content().string("Запущен синхронизированный вывод студентов"));
    }

    @Test
    void printSynchronized_ShouldReturnBadRequest_WhenNotEnoughStudents() throws Exception {
        when(studentRepository.find6StudentByOrder()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/student/print-synchronized"))
                .andExpect(content().string(containsString("Недостаточно студентов")));
    }
}