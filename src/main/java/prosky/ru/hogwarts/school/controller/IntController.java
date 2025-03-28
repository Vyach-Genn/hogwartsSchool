package prosky.ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import prosky.ru.hogwarts.school.service.IntService;

@RestController
@AllArgsConstructor
public class IntController {

    private final IntService intService;

    @GetMapping(value = "/suggestedOption")
    public Long findTheStream() {
        return intService.findTheStream();
    }

    @GetMapping(value = "/accelerator")
    public Long findTheFastestStream() {
        return intService.findTheFastestStream();
    }

    @GetMapping(value = "/formula")
    public Long getSum() {
        return intService.getSum();
    }
}
