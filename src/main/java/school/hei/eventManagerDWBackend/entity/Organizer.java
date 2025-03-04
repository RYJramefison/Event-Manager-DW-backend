package school.hei.eventManagerDWBackend.entity;

import java.time.LocalDateTime;

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

  @Override
  public String toString() {
    return "Organizer{"
        + "company='"
        + company
        + '\''
        + ", id="
        + getId()
        + // Assuming getId() is a method in User
        ", name='"
        + getName()
        + '\''
        + // Assuming getName() is a method in User
        ", email='"
        + getEmail()
        + '\''
        + // Assuming getEmail() is a method in User
        ", registrationDate="
        + getRegistrationDate()
        + // Assuming getRegistrationDate() is a method in User
        '}';
  }
}
