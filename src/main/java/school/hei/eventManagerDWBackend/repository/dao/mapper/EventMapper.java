package school.hei.eventManagerDWBackend.repository.dao.mapper;

import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.entity.StatusEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

import static school.hei.eventManagerDWBackend.entity.StatusEvent.*;

public class EventMapper {
    public StatusEvent mapEventFromResultSet(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        return switch (stringValue){
            case "DRAFT" -> DRAFT;
            case "PUBLISHED" -> PUBLISHED;
            case "CANCELED" -> CANCELED;
            default -> throw  new IllegalArgumentException("unknown event type: " + stringValue);
        };
    }
}
