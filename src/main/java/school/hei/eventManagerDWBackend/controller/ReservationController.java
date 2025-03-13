package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Reservation;
import school.hei.eventManagerDWBackend.entity.StatusReservation;
import school.hei.eventManagerDWBackend.repository.dao.Criteria;
import school.hei.eventManagerDWBackend.service.ReservationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
  private final ReservationService reservationService;

  @GetMapping
  public ResponseEntity<List<Reservation>> getAllReservations(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(reservationService.findAllReservations(page, size));
  }

  @GetMapping("/filter")
  public ResponseEntity<List<Reservation>> filterReservations(
          @RequestParam(required = false) Integer clientId,
          @RequestParam(required = false) Integer eventId,
          @RequestParam(required = false) LocalDateTime reservationDate,
          @RequestParam(required = false) LocalDateTime reservationDateMin,
          @RequestParam(required = false) LocalDateTime reservationDateMax,
          @RequestParam(required = false) StatusReservation status) {
    List<Criteria> criteria = new ArrayList<>();

    if (clientId != null){
      criteria.add(new Criteria("clientId", clientId));
    }
    if (eventId != null){
      criteria.add(new Criteria("eventId", eventId));
    }
    if (reservationDate != null){
      criteria.add(new Criteria("reservationDate", reservationDate));
    }
    if (reservationDateMin != null){
      criteria.add(new Criteria("reservationDateMin", reservationDateMin));
    }
    if (reservationDateMax != null){
      criteria.add(new Criteria("reservationDateMax", reservationDateMax));
    }
    if (status != null){
      criteria.add(new Criteria("status", status));
    }
    return ResponseEntity.ok(reservationService.filterReservations(criteria));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Reservation> getReservationById(@PathVariable int id) {
    Optional<Reservation> reservation = reservationService.findReservationById(id);
    return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
    reservationService.createReservation(reservation);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateReservation(
      @PathVariable int id, @RequestBody Reservation reservation) {
    reservation.setId(id);
    reservationService.updateReservation(reservation);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
    reservationService.deleteReservationById(id);
    return ResponseEntity.ok().build();
  }
}
