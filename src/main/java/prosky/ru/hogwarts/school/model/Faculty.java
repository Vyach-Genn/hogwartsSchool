package prosky.ru.hogwarts.school.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Faculty {

    @Schema(hidden = true)
    private Long id;
    private String name;
    private String color;


}
