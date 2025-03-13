package school.hei.eventManagerDWBackend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@Tag(name = "hello", description = "test hello")
public class Controllers {

    @GetMapping("/")
    public String hello(){
        return "hello world";
    }
}
