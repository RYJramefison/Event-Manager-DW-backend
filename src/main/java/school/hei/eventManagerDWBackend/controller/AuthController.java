package school.hei.eventManagerDWBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.eventManagerDWBackend.config.JwtTokenProvider;
import school.hei.eventManagerDWBackend.entity.*;

import school.hei.eventManagerDWBackend.service.AuthService;
import school.hei.eventManagerDWBackend.service.OrganizerService;
import school.hei.eventManagerDWBackend.utils.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private OrganizerService organizerService;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isPresent()) {
            String token =
                    jwtTokenProvider.generateToken(user.get().getEmail(), user.get().getUserType().name());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user.get());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User createdUser;
            String hashedPassword = PasswordEncoder.encode(registerRequest.getPassword());

            switch (registerRequest.getUserType()) {
                case admin:
                    Admin admin = new Admin();
                    admin.setName(registerRequest.getName());
                    admin.setEmail(registerRequest.getEmail());
                    admin.setPassword(hashedPassword);
                    admin.setUserType(UserType.admin);
                    admin.setName(registerRequest.getAdminName());
                    createdUser = authService.registerAdmin(admin);
                    break;

                case organizer:
                    Organizer organizer = new Organizer();
                    organizer.setName(registerRequest.getName());
                    organizer.setEmail(registerRequest.getEmail());
                    organizer.setPassword(hashedPassword);
                    organizer.setUserType(UserType.organizer);
                    organizer.setCompany(registerRequest.getCompany());
                    createdUser = authService.registerOrganizer(organizer);
                    break;

                case client:
                    Client client = new Client();
                    client.setName(registerRequest.getName());
                    client.setEmail(registerRequest.getEmail());
                    client.setPassword(hashedPassword);
                    client.setUserType(UserType.client);
                    createdUser = authService.registerClient(client);
                    break;

                default:
                    return ResponseEntity.badRequest().body("Type d'utilisateur invalide");
            }


            String token = jwtTokenProvider.generateToken(
                    createdUser.getEmail(),
                    createdUser.getUserType().name()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", createdUser.getId());
            response.put("userType", createdUser.getUserType());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}