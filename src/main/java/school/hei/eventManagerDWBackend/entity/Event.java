package school.hei.eventManagerDWBackend.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Event {
    private int id;
    private Organizer organizer;
    private String title;
    private String description;
    private LocalDateTime dateEvent;
    private String location;
    private StatusEvent status;

    public Event(int id, Organizer organizer, String title, String description, LocalDateTime dateEvent, String location, StatusEvent status) {
        this.id = id;
        this.organizer = organizer;
        this.title = title;
        this.description = description;
        this.dateEvent = dateEvent;
        this.location = location;
        this.status = status;
    }
    public Event() {}
}

