package school.hei.eventManagerDWBackend.entity;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public abstract class User {
  private int id;
  private String name;
  private String email;
  private String password;
  private LocalDateTime registrationDate;

  public User(int id, String name, String email, String password, LocalDateTime registrationDate) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.registrationDate = registrationDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
  }
}
