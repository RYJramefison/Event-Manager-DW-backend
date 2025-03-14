package school.hei.eventManagerDWBackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString(callSuper = true)
public class Admin extends User {

  public Admin(int id, String name, String email, String password, LocalDateTime registrationDate, UserType userType) {
    super(id, name, email, password, registrationDate, userType);
  }

  public Admin(int id, String name, String email, LocalDateTime registrationDate, UserType userType) {
    super(id, name, email, registrationDate, userType);
  }
}
