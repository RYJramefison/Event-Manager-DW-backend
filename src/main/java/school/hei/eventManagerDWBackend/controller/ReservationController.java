package school.hei.eventManagerDWBackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.eventManagerDWBackend.entity.Reservation;
import school.hei.eventManagerDWBackend.service.ReservationService;

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
