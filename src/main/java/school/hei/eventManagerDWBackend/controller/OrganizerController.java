package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.service.OrganizerService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/organizer")
public class OrganizerController {
    private final OrganizerService adminService;

    @GetMapping
    public ResponseEntity<List<Organizer>> getAllOrganizers(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.findAllOrganizers(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable int id) {
        Optional<Organizer> admin = adminService.findOrganizerById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createOrganizer(@RequestBody Organizer admin) {
        adminService.createOrganizer(admin);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrganizer(@PathVariable int id, @RequestBody Organizer admin) {
        admin.setId(id);
        adminService.updateOrganizer(admin);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable int id) {
        adminService.deleteOrganizerById(id);
        return ResponseEntity.ok().build();
    }
}
