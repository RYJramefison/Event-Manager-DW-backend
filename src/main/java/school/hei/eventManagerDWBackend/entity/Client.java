package school.hei.eventManagerDWBackend.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Client extends User{

    public Client(int id, String name, String email, Timestamp password, LocalDateTime registrationDate) {
        super(id, name, email, password, registrationDate);
    }
}
