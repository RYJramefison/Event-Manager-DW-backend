package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.service.AdminService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getAllAdmins(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createAdmin(@RequestBody Admin admin) {
        adminService.createAdmin(admin);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
        admin.setId(id);
        adminService.updateAdmin(admin);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int id) {
        adminService.deleteAdminById(id);
        return ResponseEntity.ok().build();
    }
}
