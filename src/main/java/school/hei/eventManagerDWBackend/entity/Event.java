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


}