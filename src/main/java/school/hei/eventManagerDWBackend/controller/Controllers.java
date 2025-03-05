package school.hei.eventManagerDWBackend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controllers {

    @GetMapping("/")
    public String hello(){
        return "hello world";
    }
}
