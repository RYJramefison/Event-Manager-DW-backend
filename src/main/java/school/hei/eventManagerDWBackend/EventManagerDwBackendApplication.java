package school.hei.eventManagerDWBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
    scanBasePackages = "school.hei.eventManagerDWBackend",
    exclude = {SecurityAutoConfiguration.class})
@ComponentScan({
  "school.hei.eventManagerDWBackend.controller",
  "school.hei.eventManagerDWBackend.entity",
  "school.hei.eventManagerDWBackend.repository.dao",
  "school.hei.eventManagerDWBackend.service",
  "school.hei.eventManagerDWBackend.config",
        "school.hei.eventManagerDWBackend.utils",
        "school.hei.eventManagerDWBackend.cloudinary"
})
public class EventManagerDwBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventManagerDwBackendApplication.class, args);
  }
}
