package school.hei.eventManagerDWBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"school.hei.eventManagerDWBackend.controller","school.hei.eventManagerDWBackend.entity", "school.hei.eventManagerDWBackend.repository.dao"})
public class EventManagerDwBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventManagerDwBackendApplication.class, args);
	}

}
