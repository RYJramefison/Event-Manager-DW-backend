package school.hei.eventManagerDWBackend.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Reservation {
  private int id;
  private int clientId;
  private int eventId;
  private LocalDateTime reservationDate;
  private StatusReservation statusReservation;

  public Reservation(
      int id,
      int clientId,
      int eventId,
      LocalDateTime reservationDate,
      StatusReservation statusReservation) {
    this.id = id;
    this.clientId = clientId;
    this.eventId = eventId;
    this.reservationDate = reservationDate;
    this.statusReservation = statusReservation;
  }
}
