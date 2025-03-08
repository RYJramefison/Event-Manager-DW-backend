package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.service.EventService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/event")
public class EventController {
  private final EventService eventService;

  @GetMapping
  public ResponseEntity<List<Event>> getAllEvents(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(eventService.findAllEvents(page, size));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable int id) {
    Optional<Event> event = eventService.findEventById(id);
    return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Void> createEvent(@RequestBody Event event) {
    eventService.createEvent(event);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateEvent(@PathVariable int id, @RequestBody Event event) {
    event.setId(id);
    eventService.updateEvent(event);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
    eventService.deleteEventById(id);
    return ResponseEntity.ok().build();
  }
}
