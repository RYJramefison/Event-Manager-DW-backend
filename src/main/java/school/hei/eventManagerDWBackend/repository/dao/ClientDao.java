package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.Client;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientDao implements CrudOperation<Client> {

  private final DataSource dataSource = new DataSource();

  @Override
  public void create(Client client) {
    String sql =
        "INSERT INTO client (name, email, password, registration_date) VALUES (?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt =
            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, client.getName());
      stmt.setString(2, client.getEmail());
      stmt.setString(3, client.getPassword());
      stmt.setTimestamp(4, Timestamp.valueOf(client.getRegistrationDate()));
      stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        client.setId(rs.getInt(1));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Client client) {
    String sql =
        "UPDATE client SET name = ?, email = ?, password = ?, registration_date = ? WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, client.getName());
      stmt.setString(2, client.getEmail());
      stmt.setString(3, client.getPassword());
      stmt.setTimestamp(4, Timestamp.valueOf(client.getRegistrationDate()));
      stmt.setInt(5, client.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteById(int id) {
    String sql = "DELETE FROM Client WHERE id = ?";
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
  public List<Client> getAll(int page, int size) {
    List<Client> clients = new ArrayList<>();
    String sql =
        "SELECT * FROM client_user_view LIMIT ? OFFSET ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, size);
      stmt.setInt(2, page * size);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        clients.add(
            new Client(
                rs.getInt("client_id"),
                rs.getString("client_name"),
                rs.getString("email"),
                rs.getTimestamp("registration_date").toLocalDateTime()));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return clients;
  }

  @Override
  public Optional<Client> getById(int id) {
    String sql =
        "SELECT * FROM client_user_view WHERE client_id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(
            new Client(
                rs.getInt("client_id"),
                rs.getString("client_name"),
                rs.getString("email"),
                rs.getTimestamp("registration_date").toLocalDateTime()));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }
}
