package school.hei.eventManagerDWBackend.entity;

import lombok.Getter;
import lombok.Setter;

public class Event {
    private int id;
    private Organizer organizer;
    private String title;
    private String description;
    private Timestamp dateEvent;
    private String location;
    private StatusEvent status;


}
import java.time.LocalDateTime;
@Getter
@Setter

public class Event {

  public Event(
      int id,
      Organizer organizer,
      String title,
      String description,
      LocalDateTime dateEvent,
      String location,
      StatusEvent status) 
  {
    this.id = id;
    this.organizer = organizer;
    this.title = title;
    this.description = description;
    this.dateEvent = dateEvent;
    this.location = location;
    this.status = status;
  }

  public Event(int id, String location) {
    this.id = id;
    this.location = location;
  }

 

  @Override
  public String toString() {
    return 
      "Event{"
        + "id="
        + id
        + ", organizer="
        + organizer
        + ", title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", dateEvent="
        + dateEvent
        + ", location='"
        + location
        + '\''
        + ", status="
        + status
        + '}';
  }
}

