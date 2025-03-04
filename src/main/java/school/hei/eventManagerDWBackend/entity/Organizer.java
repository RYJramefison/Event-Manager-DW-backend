package school.hei.eventManagerDWBackend.entity;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class Organizer extends User {
  private String company;

  public Organizer(
      int id,
      String name,
      String email,
      String password,
      LocalDateTime registrationDate,
      String company) {
    super(id, name, email, password, registrationDate);
    this.company = company;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }
}
