package school.hei.eventManagerDWBackend.security;


import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final JwtUtil jwtUtil;

    // Constructeur pour injecter JwtUtil
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Simuler un utilisateur dans une base de données
    private static final Map<String, String> users = new HashMap<>();

    static {
        // Ajoute un utilisateur pour tester
        users.put("fetra", "fetra"); // username = admin, password = password
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("fetra");
        String password = request.get("fetra");

        // Vérifier si l'utilisateur existe dans la "base de données"
        if (users.containsKey(username) && users.get(username).equals(password)) {
            String token = jwtUtil.generateToken(username); // Cela devrait maintenant fonctionner
            return Map.of("token", token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}


