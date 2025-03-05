package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.entity.StatusEvent;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventDao implements CrudOperation<Event> {
  private final DataSource dataSource = new DataSource();

  @Override
  public void create(Event event) {
    String sql =
        "INSERT INTO event (organizer_id, title, description, event_date, location, status) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, event.getOrganizer().getId());
      stmt.setString(2, event.getTitle());
      stmt.setString(3, event.getDescription());
      stmt.setTimestamp(4, Timestamp.valueOf(event.getDateEvent()));
      stmt.setString(5, event.getLocation());
      stmt.setString(6, event.getStatus().name());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Event event) {
    String sql =
        "UPDATE event SET organizer_id = ?, title = ?, description = ?, event_date = ?, location = ?, status = ? WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, event.getOrganizer().getId());
      stmt.setString(2, event.getTitle());
      stmt.setString(3, event.getDescription());
      stmt.setTimestamp(4, Timestamp.valueOf(event.getDateEvent()));
      stmt.setString(5, event.getLocation());
      stmt.setString(6, event.getStatus().name());
      stmt.setInt(7, event.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(Event event) {
    String sql = "DELETE FROM event WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, event.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteById(int id) {
    String sql = "DELETE FROM event WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("Événement supprimé avec succès !");
      } else {
        System.out.println("Aucun événement trouvé avec cet ID.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Event> getAll(int page, int size) {
    List<Event> events = new ArrayList<>();
    String sql =
        "SELECT e.id AS event_id,\n"
            + "       o.id AS organizer_id,\n"
            + "       o.company,\n"
            + "       e.title,\n"
            + "       e.description,\n"
            + "       e.event_date,\n"
            + "       e.location,\n"
            + "       e.status,\n"
            + "       u.id,\n"
            + "       u.name,\n"
            + "       u.email,\n"
            + "       u.password,\n"
            + "       u.registration_date\n"
            + "FROM event e\n"
            + "         INNER JOIN organizer o ON e.organizer_id = o.id\n"
            + "        INNER JOIN \"User\" u on o.user_id = u.id\n"
            + "LIMIT ? OFFSET ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, size);
      stmt.setInt(2, page * size);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Organizer organizer =
            new Organizer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getString("company"));
        Event event =
            new Event(
                rs.getInt("event_id"),
                organizer,
                rs.getString("title"),
                rs.getString("description"),
                rs.getTimestamp("event_date").toLocalDateTime(),
                rs.getString("location"),
                StatusEvent.valueOf(rs.getString("status")));
        events.add(event);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return events;
  }

  @Override
  public Optional<Event> getById(int id) {
    String sql =
        "SELECT e.id AS event_id, o.id AS organizer_id, o.company,\n"
            + "            e.title, e.description, e.event_date, e.location, e.status, U.name, U.email, U.password, U.registration_date\n"
            + "            FROM event e INNER JOIN organizer o ON e.organizer_id = o.id INNER JOIN \"User\" U on U.id = o.user_id\n"
            + "            WHERE e.id = ?;";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        Organizer organizer =
            new Organizer(
                rs.getInt("organizer_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getString("company"));
        Event event =
            new Event(
                rs.getInt("event_id"),
                organizer,
                rs.getString("title"),
                rs.getString("description"),
                rs.getTimestamp("event_date").toLocalDateTime(),
                rs.getString("location"),
                StatusEvent.valueOf(rs.getString("status")));
        return Optional.of(event);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Error retrieving event by ID", e);
    }
    return Optional.empty();
  }
}
