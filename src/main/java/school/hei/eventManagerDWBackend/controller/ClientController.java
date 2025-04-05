package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.entity.Client;
import school.hei.eventManagerDWBackend.entity.User;
import school.hei.eventManagerDWBackend.service.ClientService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(clientService.findAllClients(page, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Client>> filterClients(@RequestParam String name) {
        return ResponseEntity.ok(clientService.filterClients(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id) {
        Optional<Client> client = clientService.findClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody Client client) {
        clientService.createClient(client);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable int id, @RequestBody Client client) {
        client.setId(id);
        clientService.updateClient(client);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
        clientService.deleteClientById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public Client getCurrentClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current auth email: " + auth.getName()); // Vérifiez dans les logs
        return clientService.getCurrentClient();
    }
}
