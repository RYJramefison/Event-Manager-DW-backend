package school.hei.eventManagerDWBackend.entity;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@Setter
@EqualsAndHashCode
public class User {
    private int id;
    private String name;
    private String email;
    private Timestamp password;
    private LocalDateTime registrationDate;

}
