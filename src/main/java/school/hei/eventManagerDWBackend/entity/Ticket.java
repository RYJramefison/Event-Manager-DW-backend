package school.hei.eventManagerDWBackend.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Ticket {
  private int id;
  private String ticketCode;
  private int reservationId;
  private int ticketTypeId;
}
