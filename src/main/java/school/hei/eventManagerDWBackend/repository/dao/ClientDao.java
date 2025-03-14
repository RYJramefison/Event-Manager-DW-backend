package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.entity.Client;
import school.hei.eventManagerDWBackend.entity.UserType;
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
    String insertUserSql = "INSERT INTO \"User\" (name, email, password, user_type) VALUES (?, ?, ?, ?::user_type_enum) RETURNING id";
    String insertClientSql = "INSERT INTO client (user_id) VALUES (?)";

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);

      try (PreparedStatement psUser = conn.prepareStatement(insertUserSql)) {
        psUser.setString(1, client.getName());
        psUser.setString(2, client.getEmail());
        psUser.setString(3, client.getPassword());
        psUser.setString(4, client.getUserType().name());

        try (ResultSet rs = psUser.executeQuery()) {
          if (rs.next()) {
            int userId = rs.getInt(1);

            try (PreparedStatement psClient = conn.prepareStatement(insertClientSql)) {
              psClient.setInt(1, userId);
              psClient.executeUpdate();
            }

            conn.commit();
          } else {
            throw new SQLException("Erreur lors de l'insertion de l'utilisateur");
          }
        }
      } catch (SQLException e) {
        conn.rollback();
        System.err.println("Erreur SQL : " + e.getMessage());
        e.printStackTrace();
        throw e;
      }
    } catch (SQLException e) {
      System.err.println("Erreur lors de la création du client : " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Client client) {
    String sql =
        "UPDATE \"User\" SET name = ?, email = ?, user_type = ? WHERE user_type = 'client' id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, client.getName());
      stmt.setString(2, client.getEmail());
      stmt.setString(3, client.getPassword());
      stmt.setTimestamp(4, Timestamp.valueOf(client.getRegistrationDate()));
      stmt.setString(5, client.getUserType().name());
      stmt.setInt(6, client.getId());

      stmt.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Erreur lors de la mise à jour du client : " + e.getMessage());
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
        System.out.println("client supprimé avec succès !");
      } else {
        System.out.println("Aucun Client trouvé avec cet ID.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Client> getAll(int page, int size) {
    List<Client> clients = new ArrayList<>();
    String sql =
        "SELECT * FROM client_user_view WHERE user_type = 'client' LIMIT ? OFFSET ?";
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
                rs.getTimestamp("registration_date").toLocalDateTime(),
                UserType.valueOf(rs.getString("user_type"))
                ));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return clients;
  }

  public List<Client> filter(String criteria){
    List<Client> clients = new ArrayList<>();
    String sql = "SELECT client_id, client_name, email, registration_date,user_type FROM" +
            " client_user_view WHERE user_type = 'client' AND client_name ILIKE ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement pstm = connection.prepareStatement(sql)) {
      pstm.setString(1,'%' + criteria + '%');

      try (ResultSet res = pstm.executeQuery()){
        while (res.next()) {
          Client client = new Client(
                  res.getInt("client_id"),
                  res.getString("client_name"),
                  res.getString("email"),
                  res.getTimestamp("registration_date").toLocalDateTime(),
                  UserType.valueOf(res.getString("user_type"))
                  );
          clients.add(client);
        }
        return clients;
      }
    } catch (SQLException e) {
      throw new RuntimeException("filter client not implemented", e);
    }
  }


  @Override
  public Optional<Client> getById(int id) {
    String sql =
        "SELECT * FROM client_user_view WHERE user_type = 'client' AND client_id = ?";
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
                rs.getTimestamp("registration_date").toLocalDateTime(),
                UserType.valueOf(rs.getString("user_type"))
        ));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }
}
