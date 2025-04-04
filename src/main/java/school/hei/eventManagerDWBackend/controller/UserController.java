package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.User;
import school.hei.eventManagerDWBackend.entity.UserType;
import school.hei.eventManagerDWBackend.repository.dao.Criteria;
import school.hei.eventManagerDWBackend.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }
    @GetMapping("/filter")
    public ResponseEntity<List<User>> filtersUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDateTime registrationDate,
            @RequestParam(required = false) LocalDateTime registrationDateMin,
            @RequestParam(required = false) LocalDateTime registrationDateMax,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserType userType

    ) {
        List<Criteria> criterias = new ArrayList<>();
        if (name != null) {
            criterias.add(new Criteria("name", name));
        }
        if (registrationDate != null) {
            criterias.add(new Criteria("registrationDate", registrationDate));
        }
        if (registrationDateMin != null) {
            criterias.add(new Criteria("registrationDateMin", registrationDateMin));
        }
        if (registrationDateMax != null) {
            criterias.add(new Criteria("registrationDateMax", registrationDateMax));
        }
        if (email != null) {
            criterias.add(new Criteria("email", email));
        }
        if (userType != null) {
            criterias.add(new Criteria("userType", userType));
        }
        return ResponseEntity.ok(userService.filter(criterias));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current auth email: " + auth.getName()); // Vérifiez dans les logs
        return userService.getCurrentUser();
    }
}
