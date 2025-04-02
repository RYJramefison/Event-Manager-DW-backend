package school.hei.eventManagerDWBackend.entity;

import lombok.Getter;
import lombok.Setter;
import school.hei.eventManagerDWBackend.entity.UserType;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private UserType userType;
    private String company;  // Pour les organisateurs
    private String adminName; // Pour les admins
}