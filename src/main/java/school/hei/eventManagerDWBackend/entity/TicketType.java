package school.hei.eventManagerDWBackend.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TicketType {
  private int id;
  private String name;
  private Long price;
  private int available_quantity;
}
