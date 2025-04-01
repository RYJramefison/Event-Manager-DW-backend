package school.hei.eventManagerDWBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.eventManagerDWBackend.config.JwtTokenProvider;
import school.hei.eventManagerDWBackend.entity.LoginRequest;
import school.hei.eventManagerDWBackend.entity.User;
import school.hei.eventManagerDWBackend.service.AuthService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isPresent()) {
            String token = jwtTokenProvider.generateToken(
                    user.get().getEmail(),
                    user.get().getUserType().name()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user.get());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }
}