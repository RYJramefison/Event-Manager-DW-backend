package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Ticket;
import school.hei.eventManagerDWBackend.service.TicketService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/ticket")
public class TicketController {
  private final TicketService ticketService;

  @GetMapping
  public ResponseEntity<List<Ticket>> getAllTickets(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(ticketService.findAllTickets(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Ticket> getTicketById(@PathVariable int id) {
    Optional<Ticket> ticket = ticketService.findTicketById(id);
    return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Void> createTicket(@RequestBody Ticket ticket) {
    ticketService.createTicket(ticket);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateTicket(@PathVariable int id, @RequestBody Ticket ticket) {
    ticket.setId(id);
    ticketService.updateTicket(ticket);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
    ticketService.deleteTicketById(id);
    return ResponseEntity.ok().build();
  }
}
