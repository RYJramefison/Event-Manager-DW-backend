package school.hei.eventManagerDWBackend.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Reservation {
  private int id;
  private int clientId;
  private int eventId;
  private LocalDateTime reservationDate;
  private StatusReservation statusReservation;
}
