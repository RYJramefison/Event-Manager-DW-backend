package school.hei.eventManagerDWBackend.entity;

import java.sql.Timestamp;

public class Event {
    private int id;
    private Organizer organizer;
    private String title;
    private String description;
    private Timestamp dateEvent;
    private String location;
    private StatusEvent status;


    public Event(int id, Organizer organizer, String title, String description, Timestamp dateEvent, String location, StatusEvent status) {
        this.id = id;
        this.organizer = organizer;
        this.title = title;
        this.description = description;
        this.dateEvent = dateEvent;
        this.location = location;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Timestamp dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StatusEvent getStatus() {
        return status;
    }

    public void setStatus(StatusEvent status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", organizer=" + organizer +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateEvent=" + dateEvent +
                ", location='" + location + '\'' +
                ", status=" + status +
                '}';
    }
}
