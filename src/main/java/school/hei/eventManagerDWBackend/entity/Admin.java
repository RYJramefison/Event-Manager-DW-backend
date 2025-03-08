package school.hei.eventManagerDWBackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString(callSuper = true)
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

    public Admin(int adminId, String adminName, String email, LocalDateTime registrationDate) {
        super(adminId, adminName, email, registrationDate);
        this.adminName = adminName;
    }

    public String getAdminName() {
    return adminName;
  }

  public void setAdminName(String adminName) {
    this.adminName = adminName;
  }
}
