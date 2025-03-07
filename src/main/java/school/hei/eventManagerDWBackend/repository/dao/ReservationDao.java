package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.Reservation;
import school.hei.eventManagerDWBackend.entity.StatusReservation;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationDao implements CrudOperation<Reservation> {

  private final DataSource dataSource = new DataSource();

  @Override
  public void create(Reservation reservation) {
    String sql =
        "INSERT INTO reservation (client_id, event_id, reservation_date, status) VALUES (?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setInt(1, reservation.getClientId());
      stmt.setInt(2, reservation.getEventId());
      stmt.setTimestamp(3, Timestamp.valueOf(reservation.getReservationDate()));
      stmt.setString(4, reservation.getStatusReservation().name());
      stmt.executeUpdate();

      // Récupérer l'ID généré après insertion
      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        reservation.setId(rs.getInt(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Reservation reservation) {
    String sql =
        "UPDATE reservation SET client_id = ?, event_id = ?, reservation_date = ?, status = ? WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, reservation.getClientId());
      stmt.setInt(2, reservation.getEventId());
      stmt.setTimestamp(3, Timestamp.valueOf(reservation.getReservationDate()));
      stmt.setString(4, reservation.getStatusReservation().name());
      stmt.setInt(5, reservation.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteById(int id) {
    String sql = "DELETE FROM Reservation WHERE id = ?";
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
  public List<Reservation> getAll(int page, int size) {
    List<Reservation> reservations = new ArrayList<>();
    String sql =
        "SELECT id, client_id, event_id, reservation_date, status FROM reservation LIMIT ? OFFSET ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, size);
      stmt.setInt(2, page * size);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        reservations.add(
            new Reservation(
                rs.getInt("id"),
                rs.getInt("client_id"),
                rs.getInt("event_id"),
                rs.getTimestamp("reservation_date").toLocalDateTime(),
                StatusReservation.valueOf(rs.getString("status"))));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return reservations;
  }

  @Override
  public Optional<Reservation> getById(int id) {
    String sql =
        "SELECT id, client_id, event_id, reservation_date, status FROM reservation WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(
            new Reservation(
                rs.getInt("id"),
                rs.getInt("client_id"),
                rs.getInt("event_id"),
                rs.getTimestamp("reservation_date").toLocalDateTime(),
                StatusReservation.valueOf(rs.getString("status"))));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }
}
