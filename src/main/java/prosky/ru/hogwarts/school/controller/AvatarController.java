package prosky.ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prosky.ru.hogwarts.school.model.Avatar;
import prosky.ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
@RestController
@RequestMapping("avatar")
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(
            @PathVariable Long studentId,
            @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("Слишком большой файл");
        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable Long studentId) throws IOException {
        Avatar avatar = avatarService.findAvatar(studentId);
        Path path = Path.of(avatar.getFilePath());

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .contentLength(avatar.getFileSize())
                .body(resource);
    }

    @GetMapping(value = "/{studentId}/avatar-from-db")
    public ResponseEntity<byte[]> getAvatarData(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping
    public ResponseEntity<Page<Avatar>> getAllOfPageAvatar(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "2") Integer pageSize) {
        return ResponseEntity.ok(avatarService.findAllAvatars(PageRequest.of(pageNumber, pageSize)));
    }
}
