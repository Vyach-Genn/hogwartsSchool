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
import prosky.ru.hogwarts.school.controller.FacultyController;
import prosky.ru.hogwarts.school.exception.NotFoundException;
import prosky.ru.hogwarts.school.model.Faculty;
import prosky.ru.hogwarts.school.repository.FacultyRepository;
import prosky.ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static prosky.ru.hogwarts.school.service.ContansForTest.GRYFFINDOR;
import static prosky.ru.hogwarts.school.service.ContansForTest.SLYTHERIN;

@WebMvcTest(FacultyController.class)
class SchoolHogwartsFacultyWebMvcApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @Test
    public void postFacultyTest() throws Exception {
        Faculty faculty = GRYFFINDOR;

        JSONObject userObject = new JSONObject();
        userObject.put("id", faculty.getId());
        userObject.put("name", faculty.getName());
        userObject.put("color", faculty.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        Faculty faculty = GRYFFINDOR;

        when(facultyRepository.existsById(faculty.getId())).thenReturn(true);
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .param("facultyId", faculty.getId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }

    @Test
    public void getFacultyByColorTest() throws Exception {
        Faculty faculty = GRYFFINDOR;

        when(facultyRepository.findByColorIgnoreCase(faculty.getColor())).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .param("color", faculty.getColor())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }

    @Test
    public void getFacultyByIdTest_NotFound() throws Exception {
        Long id = 999L;

        when(facultyRepository.findById(id)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .param("facultyId", id.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFacultyTest() throws Exception {
        Long id = 1L;
        String updatedName = "Updated Faculty_1";
        String updatedColor = "Yellow";

        JSONObject updatedUserObject = new JSONObject();
        updatedUserObject.put("id", id);
        updatedUserObject.put("name", updatedName);
        updatedUserObject.put("color", updatedColor);

        Faculty updatedFaculty = new Faculty(id, updatedName, updatedColor);

        when(facultyRepository.existsById(id)).thenReturn(true);
        when(facultyRepository.findById(id)).thenReturn(Optional.of(updatedFaculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/{id}", id)
                        .content(updatedUserObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(updatedName))
                .andExpect(jsonPath("$.color").value(updatedColor));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Long id = 1L;

        when(facultyRepository.existsById(id)).thenReturn(true);
        doNothing().when(facultyRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        Faculty faculty1 = GRYFFINDOR;
        Faculty faculty2 = SLYTHERIN;

        when(facultyRepository.findAll()).thenReturn(List.of(faculty1, faculty2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(faculty1.getId()))
                .andExpect(jsonPath("$[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$[1].id").value(faculty2.getId()))
                .andExpect(jsonPath("$[1].name").value(faculty2.getName()))
                .andExpect(jsonPath("$[1].color").value(faculty2.getColor()));
    }

}