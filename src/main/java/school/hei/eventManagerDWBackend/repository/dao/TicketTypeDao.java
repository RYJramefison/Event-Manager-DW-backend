package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.TicketType;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketTypeDao {
  private final DataSource dataSource = new DataSource();

  public void createTicketType(TicketType ticket)
      throws SQLException {
    String sql =
        "INSERT INTO TicketType (event_id, name, price, available_quantity) VALUES (?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, ticket.getEventId());
      stmt.setString(2, ticket.getName());
      stmt.setDouble(3, ticket.getPrice());
      stmt.setInt(4, ticket.getAvailable_quantity());
      stmt.executeUpdate();
    }
  }

  public List<TicketType> findByEventId(int eventId) throws SQLException {
    List<TicketType> ticketTypes = new ArrayList<>();
    String sql = "SELECT * FROM TicketType WHERE event_id = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, eventId);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        ticketTypes.add(mapRowToTicketType(rs));
      }
    }
    return ticketTypes;
  }

  public Optional<TicketType> findById(int id) {
    String sql = "SELECT id, ticket_code, reservation_id, ticket_type_id FROM ticket WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(
            new TicketType(
                rs.getInt("id"),
                rs.getInt("event_id"),
                rs.getString("name"),
                rs.getLong("price"),
                rs.getInt("available_quantity")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }

  public void updateTicketType(int id, String name, double price, int availableQuantity)
      throws SQLException {
    String sql = "UPDATE TicketType SET name = ?, price = ?, available_quantity = ? WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, name);
      stmt.setDouble(2, price);
      stmt.setInt(3, availableQuantity);
      stmt.setInt(4, id);
      stmt.executeUpdate();
    }
  }

  public void deleteTicketType(int id) throws SQLException {
    String sql = "DELETE FROM TicketType WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    }
  }

  public List<TicketType> getAllTicketTypes(int page, int size) throws SQLException {
    List<TicketType> ticketTypes = new ArrayList<>();
    String sql = "SELECT * FROM TicketType LIMIT ? OFFSET ?"; // Corrected query for pagination
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, size);
      stmt.setInt(2, page * size);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) { // Removed unnecessary semicolon here
        ticketTypes.add(mapRowToTicketType(rs));
      }
    }
    return ticketTypes;
  }

  private TicketType mapRowToTicketType(ResultSet rs) throws SQLException {
    // Changed from getLong to getDouble for price type consistency
    return new TicketType(
        rs.getInt("id"),
        rs.getInt("event_id"),
        rs.getString("name"),
        rs.getLong("price"),
        rs.getInt("available_quantity"));
  }
}
