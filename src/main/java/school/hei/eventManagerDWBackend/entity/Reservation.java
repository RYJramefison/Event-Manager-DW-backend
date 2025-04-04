package school.hei.eventManagerDWBackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Reservation {
  private int id;
  private int clientId;
  private int eventId;
  private List<Ticket> ticketList;
  private LocalDateTime reservationDate;
  private StatusReservation statusReservation;

  public Reservation(int id, int clientId, int eventId, List<Ticket> ticketList, LocalDateTime reservationDate, StatusReservation statusReservation) {
    this.id = id;
    this.clientId = clientId;
    this.eventId = eventId;
    this.ticketList = ticketList;
    this.reservationDate = reservationDate;
    this.statusReservation = statusReservation;
  }

  public Reservation(){}
}
