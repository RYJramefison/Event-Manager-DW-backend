package school.hei.eventManagerDWBackend.entity;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class Admin extends User {
  private String adminName;

  public Admin(
      int id,
      String name,
      String email,
      String password,
      LocalDateTime registrationDate,
      String adminName) {
    super(id, name, email, password, registrationDate);
    this.adminName = adminName;
  }

  public String getAdminName() {
    return adminName;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }

  //  @Override
  //  public String toString() {
  //    return "Admin{" +
  //           "adminName='" + adminName + '\'' +
  //           ", " + super.toString() +
  //           '}';
  //  }
}
