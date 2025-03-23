package prosky.ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import prosky.ru.hogwarts.school.service.IntService;

@RestController
@AllArgsConstructor
public class IntController {

    private final IntService intService;

    @GetMapping
    public ResponseEntity<Long> findTheStream() {
        return ResponseEntity.ok(intService.findTheStream());
    }

    @GetMapping(value = "/accelerator")
    public ResponseEntity<Long> findTheFastestStream() {
        return ResponseEntity.ok(intService.findTheFastestStream());
    }

    @GetMapping(value = "/formula")
    public ResponseEntity<Long> getSum() {
        return ResponseEntity.ok(intService.getSum());
    }
}
