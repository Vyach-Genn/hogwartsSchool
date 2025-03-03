package prosky.ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Avatar {

    @Id
    @GeneratedValue
    private Long id;

    private String filePath;

    private long fileSize;

    private String mediaType;

    @Lob
    private byte[] data;

    @OneToOne()
    @JoinColumn(name = "student_id")
    private Student student;

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return fileSize == avatar.fileSize &&
                Objects.equals(id, avatar.id) &&
                Objects.equals(filePath, avatar.filePath) &&
                Objects.equals(mediaType, avatar.mediaType) &&
                Objects.deepEquals(data, avatar.data) &&
                Objects.equals(student, avatar.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filePath, fileSize, mediaType, Arrays.hashCode(data), student);
    }
}
