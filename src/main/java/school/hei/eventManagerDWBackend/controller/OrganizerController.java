package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.entity.User;
import school.hei.eventManagerDWBackend.repository.dao.Criteria;
import school.hei.eventManagerDWBackend.service.OrganizerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/organizer")
@CrossOrigin(origins = "http://localhost:3000")
public class OrganizerController {
    private final OrganizerService organizerService;

    @GetMapping
    public ResponseEntity<List<Organizer>> getAllOrganizers(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(organizerService.findAllOrganizers(page, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Organizer>> filterOrganizers(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String company) {
        List<Criteria> criteria = new ArrayList<>();

        if (name != null){
            criteria.add(new Criteria("name", name));
        }
        if (company != null){
            criteria.add(new Criteria("company", company));
        }
        return ResponseEntity.ok(organizerService.filterOrganizer(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable int id) {
        Optional<Organizer> organizer = organizerService.findOrganizerById(id);
        return organizer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createOrganizer(@RequestBody Organizer organizer) {
        organizerService.createOrganizer(organizer);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrganizer(@PathVariable int id, @RequestBody Organizer organizer) {
        organizer.setId(id);
        organizerService.updateOrganizer(organizer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable int id) {
        organizerService.deleteOrganizerById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public Organizer getCurrentOrganizer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current auth email: " + auth.getName()); // Vérifiez dans les logs
        return organizerService.getCurrentOrganizer();
    }

}
