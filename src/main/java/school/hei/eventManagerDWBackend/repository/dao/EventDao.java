package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EventDao {
  private final DataSource dataSource = new DataSource();

  public Optional<Event> getById(int id) {
    String sql = "SELECT * FROM Event WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(
            new Event(rs.getInt("id"),
                    rs.getString("location")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
