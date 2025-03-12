package prosky.ru.hogwarts.school.template;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import prosky.ru.hogwarts.school.controller.FacultyController;
import prosky.ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolHogwartsFacultyForTestRestTemplateApplicationTest {

    private static final String BASE_URL = "http://localhost:";
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertNotNull(facultyController);
    }

    @Test
    void testGetFacultyById() {
        Long facultyId = 1L;
        String url = BASE_URL + port + "/faculty?id=" + facultyId;

        String response = this.restTemplate.getForObject(url, String.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).contains("\"id\":" + facultyId);
    }

    @Test
    void testGetfacultyByColor() {
        String color = "red";
        String url = BASE_URL + port + "/faculty?color=" + color;

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    void testGetfacultyByName() {
        String name = "Faculty1";
        String url = BASE_URL + port + "/faculty?name=" + name;

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetAllfacultys() {
        String url = BASE_URL + port + "/faculty";

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetStudentByFaculty() {
        Long facultyId = 52L;
        String url = BASE_URL + port + "/faculty/" + facultyId + "/students";

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getBody()).isNotNull();

    }

    @Test
    void testCreatefaculty() {
        Faculty faculty = new Faculty(5L, "Faculty_5", "Yellow");
        String url = BASE_URL + port + "/faculty";

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, faculty, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testupdatefaculty() {
        Long id = 302L;
        Faculty newfaculty = new Faculty(id, "Updated Faculty_5", "Red");
        String url = BASE_URL + port + "/faculty/" + id;

        ResponseEntity<Faculty> response = this.restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(newfaculty), Faculty.class);

        Faculty returnedfaculty = response.getBody();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(returnedfaculty).isNotNull();
        Assertions.assertThat(returnedfaculty.getId()).isEqualTo(id);
        Assertions.assertThat(returnedfaculty.getName()).isEqualTo(newfaculty.getName());
        Assertions.assertThat(returnedfaculty.getColor()).isEqualTo(newfaculty.getColor());
    }

    @Test
    void testDeletefaculty() {
        Long id = 302L;
        String url = BASE_URL + port + "/faculty/" + id;

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}