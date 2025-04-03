package school.hei.eventManagerDWBackend.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    private String imageUrl;

    public Event(int id, Organizer organizer, String title, String description, LocalDateTime dateEvent, String location, StatusEvent status, String imageUrl) {
        this.id = id;
        this.organizer = organizer;
        this.title = title;
        this.description = description;
        this.dateEvent = dateEvent;
        this.location = location;
        this.status = status;
        this.imageUrl = imageUrl;
    }
    public Event() {}

    public static List<Event> getLast6Events(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return List.of();
        }

        return events.stream()
                .sorted(Comparator.comparing(Event::getDateEvent).reversed())
                .limit(6)
                .collect(Collectors.toList());
    }
}

