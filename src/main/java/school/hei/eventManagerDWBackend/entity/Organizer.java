package school.hei.eventManagerDWBackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class Organizer extends User {
  private String company;

  public Organizer(
      int id,
      String name,
      String email,
      String password,
      LocalDateTime registrationDate,
      UserType userType,
      String company) {
    super(id, name, email, password, registrationDate, userType);
    this.company = company;
  }

  public Organizer(
      int eventId, String eventName, String email, LocalDateTime registrationDate, UserType userType, String company) {
    super(eventId, eventName, email, registrationDate, userType);
    this.company = company;
  }

  public Organizer() {
    super();
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }
}
