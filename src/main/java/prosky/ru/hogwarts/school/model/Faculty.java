package prosky.ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Faculty {

    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String studentName;

    @ToString.Exclude
    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Faculty(Long id, String name, String color, String studentName) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.studentName = studentName;
    }

}
