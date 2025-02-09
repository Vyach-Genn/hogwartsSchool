package prosky.ru.hogwarts.school.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Student {

    @Schema(hidden = true)
    private Long id;
    private String name;
    private int age;

}

