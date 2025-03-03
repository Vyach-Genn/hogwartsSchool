package prosky.ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prosky.ru.hogwarts.school.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findAvatarByStudentId(Long studentId);
}
