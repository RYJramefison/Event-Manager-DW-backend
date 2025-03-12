package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.entity.StatusEvent;
import school.hei.eventManagerDWBackend.repository.dao.Criteria;
import school.hei.eventManagerDWBackend.service.EventService;


import java.time.LocalDateTime;
import java.util.ArrayList;
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

  @GetMapping("/filter")
  public ResponseEntity<List<Event>> filterEvents(
          @RequestParam(required = false) String company,
          @RequestParam(required = false) LocalDateTime dateEvent,
          @RequestParam(required = false) LocalDateTime dateEventMin,
          @RequestParam(required = false) LocalDateTime dateEventMax,
          @RequestParam(required = false) String title,
          @RequestParam(required = false) StatusEvent status,
          @RequestParam(required = false) String location

  ) {
    List<Criteria> criterias = new ArrayList<>();
    if (company != null) {
      criterias.add(new Criteria("company", company));
    }
    if (dateEvent != null) {
      criterias.add(new Criteria("dateEvent", dateEvent));
    }
    if (dateEventMin != null) {
      criterias.add(new Criteria("dateEventMin", dateEventMin));
    }
    if (dateEventMax != null) {
      criterias.add(new Criteria("dateEventMax", dateEventMax));
    }
    if (title != null) {
      criterias.add(new Criteria("title", title));
    }
    if (status != null) {
      criterias.add(new Criteria("status", status));
    }
    if (location != null) {
      criterias.add(new Criteria("location", location));
    }
    return ResponseEntity.ok(eventService.filterEvent(criterias));
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
