package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.entity.StatusEvent;
import school.hei.eventManagerDWBackend.entity.TicketType;
import school.hei.eventManagerDWBackend.repository.dao.Criteria;
import school.hei.eventManagerDWBackend.service.EventService;
import school.hei.eventManagerDWBackend.service.TicketService;
import school.hei.eventManagerDWBackend.service.TicketTypeService;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/event")
public class EventController {
  private final EventService eventService;
  private final TicketTypeService ticketTypeService;

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

  @GetMapping("/size")
  public ResponseEntity<Integer> getLastInsertId() {
    return ResponseEntity.ok(eventService.getLastInsertId()) ;

  }

  @PutMapping("/image/{id}")
  public ResponseEntity<Void> uploadImage(@PathVariable final int id, @RequestBody final MultipartFile file) {
    eventService.upload(id, file);
    return ResponseEntity.ok().build();
  }

  @PostMapping
  public ResponseEntity<Event> createEvent(
          @RequestPart("event") Event event,
          @RequestPart(value = "image", required = false) MultipartFile imageFile) {

    Event createdEvent = eventService.createEvent(event, imageFile);
    return ResponseEntity.ok(createdEvent);
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

  @GetMapping("/last6")
  public List<Event> getLast6Events() {
    return eventService.getLast6Events();
  }

  @GetMapping("/last9")
  public List<Event> getLast10Events() {
    return eventService.getLas9Events();
  }

  @GetMapping("/{eventId}/available")
  public List<TicketType> getAvailableTicketsForEvent(@PathVariable int eventId) throws SQLException {
    return ticketTypeService.getAvailableTicketTypesForEvent(eventId);
  }

  @GetMapping("organizer/{organizerId}")
  public ResponseEntity<List<Event>> getEventsByOrganizer(@PathVariable int organizerId) {
    List<Event> events = eventService.getEventsByOrganizerId(organizerId);

    if (events.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(events);
  }
}
