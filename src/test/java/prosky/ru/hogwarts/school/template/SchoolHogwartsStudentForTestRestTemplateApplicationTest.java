/*
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
import prosky.ru.hogwarts.school.controller.StudentController;
import prosky.ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolHogwartsStudentForTestRestTemplateApplicationTest {

    private static final String BASE_URL = "http://localhost:";
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertNotNull(studentController);
    }

    @Test
    void testGetStudentById() {
        Long studentId = 1L;
        String url = BASE_URL + port + "/student?id=" + studentId;

        String response = this.restTemplate.getForObject(url, String.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).contains("\"id\":" + studentId);
    }

    @Test
    void testGetStudentByIdNotFound() {
        Long studentId = 999L;
        String url = BASE_URL + port + "/student?id=" + studentId;

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetStudentByAge() {
        int age = 20;
        String url = BASE_URL + port + "/student?age=" + age;

        String response = this.restTemplate.getForObject(url, String.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).contains("\"age\":" + age);
    }

    @Test
    void testGetStudentByAgeRange() {
        int minAge = 18;
        int maxAge = 25;
        String url = BASE_URL + port + "/student?minAge=" + minAge + "&maxAge=" + maxAge;

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetAllStudents() {
        String url = BASE_URL + port + "/student";

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void TestGetFacultyByStudentId() {
        Long studentId = 3L;
        String url = BASE_URL + port + "/student/" + studentId + "/faculty";

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();

    }

    @Test
    void testGetFacultyByStudentIdNotFound() {
        Long studentId = 999L;
        String url = BASE_URL + port + "/student/" + studentId + "/faculty";

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateStudent() {
        Student student = new Student(1L, "Harry", 15);
        String url = BASE_URL + port + "/student";

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, student, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testupdateStudent() {
        Long id = 1L;
        Student newStudent = new Student(id, "Updated Harry", 13);
        String url = BASE_URL + port + "/student/" + id;

        ResponseEntity<Student> response = this.restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(newStudent), Student.class);

        Student returnedStudent = response.getBody();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(returnedStudent).isNotNull();
        Assertions.assertThat(returnedStudent.getId()).isEqualTo(id);
        Assertions.assertThat(returnedStudent.getName()).isEqualTo(newStudent.getName());
        Assertions.assertThat(returnedStudent.getAge()).isEqualTo(newStudent.getAge());
    }

    @Test
    void testDeleteStudent() {
        Long id = 1L;
        String url = BASE_URL + port + "/student/" + id;

        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}*/
