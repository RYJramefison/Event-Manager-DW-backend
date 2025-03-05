package school.hei.eventManagerDWBackend.entity;

import lombok.*;

@Getter
@Setter
@ToString
public class Ticket {
  private int id;
  private String ticketCode;
  private int reservationId;
  private int ticketTypeId;

  public Ticket(int id, String ticketCode, int reservationId, int ticketTypeId) {
    this.id = id;
    this.ticketCode = ticketCode;
    this.reservationId = reservationId;
    this.ticketTypeId = ticketTypeId;
  }
}
