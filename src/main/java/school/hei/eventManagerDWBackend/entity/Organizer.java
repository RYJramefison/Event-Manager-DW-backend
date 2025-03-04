package school.hei.eventManagerDWBackend.entity;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@ToString
@Setter

public class Organizer extends User{
    private String company;

    public Organizer(int id, String name, String email, Timestamp password, LocalDateTime registrationDate, String company) {
        super(id, name, email, password, registrationDate);
        this.company = company;
    }
}
