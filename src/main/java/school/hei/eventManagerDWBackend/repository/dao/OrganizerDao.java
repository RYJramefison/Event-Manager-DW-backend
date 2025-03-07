package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrganizerDao implements CrudOperation<Organizer> {

  private final DataSource dataSource = new DataSource();

  @Override
  public void create(Organizer organizer) {
    String sql = "INSERT INTO organizer (user_id, company) VALUES (?, ?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setInt(1, organizer.getId());
      stmt.setString(2, organizer.getCompany());
      stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        organizer.setId(rs.getInt(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Organizer organizer) {
    String sql = "UPDATE organizer SET company = ? WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, organizer.getCompany());
      stmt.setInt(2, organizer.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Organizer organizer) {
    String sql = "DELETE FROM organizer WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, organizer.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Organizer> getAll(int page, int size) {
    List<Organizer> organizers = new ArrayList<>();
    String sql =
        "SELECT o.id AS event_id, u.name, u.email, u.password, u.registration_date, o.company "
            + "FROM organizer o "
            + "JOIN \"User\" u ON o.user_id = u.id "
            + "LIMIT ? OFFSET ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, size);
      stmt.setInt(2, page * size);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        organizers.add(
            new Organizer(
                rs.getInt("event_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getString("company")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return organizers;
  }

  @Override
  public Optional<Organizer> getById(int id) {
    String sql =
        "SELECT o.id AS event_id, u.name, u.email, u.password, u.registration_date, o.company "
            + "FROM organizer o "
            + "JOIN \"User\" u ON o.user_id = u.id "
            + "WHERE o.id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(
            new Organizer(
                rs.getInt("event_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getString("company")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }
}
