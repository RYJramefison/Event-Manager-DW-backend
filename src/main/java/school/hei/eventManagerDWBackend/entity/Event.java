package school.hei.eventManagerDWBackend.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Event {
  private int id;
  private Organizer organizer;
  private String title;
  private String description;
  private LocalDateTime dateEvent;
  private String location;
  private StatusEvent status;
}
