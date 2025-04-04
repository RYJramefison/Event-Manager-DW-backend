package school.hei.eventManagerDWBackend.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.TicketType;
import school.hei.eventManagerDWBackend.service.TicketTypeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticketType")
@AllArgsConstructor
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;



    @GetMapping
    public ResponseEntity<List<TicketType>> getAllTicketTypes(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws Exception {
        return ResponseEntity.ok(ticketTypeService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketType> getTicketTypeById(@PathVariable int id) throws Exception {
        Optional<TicketType> ticket = ticketTypeService.findById(id);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<List<TicketType>> getByEventId(@PathVariable int id) throws Exception {
        List<TicketType> ticket = ticketTypeService.findByEventId(id);
        return ResponseEntity.ok().body( ticket);
    }

    @PostMapping
    public ResponseEntity<Void> createTicket(@RequestBody TicketType ticket) throws Exception  {
        ticketTypeService.save(ticket);
        return ResponseEntity.ok().build();
    }

}
