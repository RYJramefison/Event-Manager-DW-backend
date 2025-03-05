package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.repository.dao.mapper.EventMapper;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventCrudOperations implements CrudOperation<Event>{
    private DataSource dataSource = new DataSource();
    private EventMapper eventMapper = new EventMapper();

    @Override
    public List<Event> getAll(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page must be greater than 0 but actual is " + page);
        }

        String sql = "SELECT e.id AS event_id, o.company, e.title, e.description, e.event_date, e.location," +
                " e.status FROM event e INNER JOIN organizer o ON e.id = o.id LIMIT ? OFFSET ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, size);
            pstm.setInt(2, size * (page -1));

            try (ResultSet rs = pstm.executeQuery()) {
                List<Event> events = new ArrayList<>();
                while (rs.next()) {
                    Organizer organizer = new Organizer(
                            rs.getString("company")
                    );

                    Event event = new Event();
                    event.setId(rs.getInt("event_id"));
                    event.setOrganizer(organizer);
                    event.setTitle(rs.getString("title"));
                    event.setDescription(rs.getString("description"));
                    event.setDateEvent(Timestamp.valueOf(rs.getTimestamp("event_date").toLocalDateTime()).toLocalDateTime());
                    event.setLocation(rs.getString("location"));
                    event.setStatus(eventMapper.mapEventFromResultSet(rs.getString("status")));

                    events.add(event);
                }
                return events;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event getById(int id) {

        return null;
    }


    @Override
    public void create(Event event) {

    }

    @Override
    public void update(Event entity) {

    }

    @Override
    public void delete(Event entity) {

    }


}
