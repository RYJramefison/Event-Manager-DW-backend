package school.hei.eventManagerDWBackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@ToString
@Setter


public class Admin extends User{
    private String adminName;

    public Admin(int id, String name, String email, Timestamp password, LocalDateTime registrationDate, String adminName) {
        super(id, name, email, password, registrationDate);
        this.adminName = adminName;
    }
}
