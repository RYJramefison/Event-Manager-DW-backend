package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.entity.UserType;
import school.hei.eventManagerDWBackend.repository.db.DataSource;
import school.hei.eventManagerDWBackend.utils.PasswordEncoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrganizerDao implements CrudOperation<Organizer> {

  private final DataSource dataSource = new DataSource();
  @Override
  public void create(Organizer organizer) {
    String hashedPassword = PasswordEncoder.encode(organizer.getPassword());

    String insertUserSql = "INSERT INTO \"User\" (name, email, password, user_type) VALUES (?, ?, ?, ?::user_type_enum) RETURNING id";
    String insertOrganizerSql = "INSERT INTO organizer (user_id, company) VALUES (?, ?)";

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);

      try (PreparedStatement psUser = conn.prepareStatement(insertUserSql)) {
        psUser.setString(1, organizer.getName());
        psUser.setString(2, organizer.getEmail());
        psUser.setString(3, hashedPassword);
        psUser.setString(4, organizer.getUserType().name()); // Ajout du user_type
        ResultSet rs = psUser.executeQuery();
        if (rs.next()) {
          int userId = rs.getInt(1);

          try (PreparedStatement psOrganizer = conn.prepareStatement(insertOrganizerSql)) {
            psOrganizer.setInt(1, userId);
            psOrganizer.setString(2, organizer.getCompany());
            psOrganizer.executeUpdate();
          }

          conn.commit();
        } else {
          throw new SQLException("Erreur lors de l'insertion de l'utilisateur");
        }
      } catch (SQLException e) {
        conn.rollback();
        throw e;
      }
    } catch (SQLException e) {
      System.err.println("Erreur lors de la création de l'organizer : " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public Organizer save(Organizer organizer) {
    // 1. D'abord créer l'User
    String insertUserSql = "INSERT INTO \"User\" (name, email, password, user_type, registration_date) VALUES (?, ?, ?, ?::user_type_enum,NOW()) RETURNING id";
    String insertOrganizerSql = "INSERT INTO Organizer (user_id, company) VALUES (?, ?) RETURNING *";

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);

      // Insertion User
      try (PreparedStatement psUser = conn.prepareStatement(insertUserSql)) {
        psUser.setString(1, organizer.getName());
        psUser.setString(2, organizer.getEmail());
        psUser.setString(3, organizer.getPassword());
        psUser.setString(4, organizer.getUserType().name());

        ResultSet rs = psUser.executeQuery();
        if (rs.next()) {
          int userId = rs.getInt("id");
          organizer.setId(userId);

          // Insertion Organizer
          try (PreparedStatement psOrganizer = conn.prepareStatement(insertOrganizerSql)) {
            psOrganizer.setInt(1, userId);
            psOrganizer.setString(2, organizer.getCompany());

            ResultSet rsOrg = psOrganizer.executeQuery();
            if (rsOrg.next()) {
              organizer.setCompany(rsOrg.getString("company"));
              conn.commit();
              return organizer;
            }
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Erreur création organizer", e);
    }
    throw new RuntimeException("Échec création organizer");
  }

  @Override
  public void update(Organizer organizer) {
    String updateUserSql = "UPDATE \"User\" SET name = ?, password = ? WHERE id = ? AND user_type = 'organizer'";
    String updateOrganizerSql = "UPDATE Organizer SET company = ? WHERE user_id = ?";

    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(false);

      try (PreparedStatement userStmt = connection.prepareStatement(updateUserSql);
           PreparedStatement organizerStmt = connection.prepareStatement(updateOrganizerSql)) {

        // Mise à jour de "User"
        userStmt.setString(1, organizer.getName());
        userStmt.setString(2, organizer.getPassword());
        userStmt.setInt(3, organizer.getId());
        userStmt.executeUpdate();

        // Mise à jour de "Organizer"
        organizerStmt.setString(1, organizer.getCompany());
        organizerStmt.setInt(2, organizer.getId());
        organizerStmt.executeUpdate();

        connection.commit();
      } catch (SQLException e) {
        connection.rollback();
        throw new RuntimeException("Erreur lors de la mise à jour de l'organizer", e);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Problème de connexion à la base de données", e);
    }
  }


  public void deleteById(int id) {
    String sql = "DELETE FROM Organizer WHERE id = ?";
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
  public List<Organizer> getAll(int page, int size) {
    List<Organizer> organizers = new ArrayList<>();
    String sql =
        "SELECT * FROM organizer_user_view WHERE user_type = 'organizer' LIMIT ? OFFSET ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, size);
      stmt.setInt(2, page * size);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        organizers.add(
            new Organizer(
                rs.getInt("organizer_id"),
                rs.getString("organizer_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                UserType.valueOf(rs.getString("user_type")),
                rs.getString("company")
        ));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return organizers;
  }

  public List<Organizer> filter(List<Criteria> criterias) {
    List<Organizer> organizers = new ArrayList<>();
    String sql = "SELECT * FROM organizer_user_view WHERE user_type = 'organizer' AND 1=1";

    for (Criteria criteria : criterias) {
      if ("name".equals(criteria.getColumn())){
        sql += " AND organizer_name ILIKE '%" + criteria.getValue() + "%'";
      }
      else if ("company".equals(criteria.getColumn())){
        sql += " AND company ILIKE '%" + criteria.getValue() + "%'";
      }
    }
    try (Connection connection = dataSource.getConnection();
         Statement stmt = connection.createStatement()) {
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        organizers.add(
                new Organizer(
                        rs.getInt("organizer_id"),
                        rs.getString("organizer_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registration_date").toLocalDateTime(),
                        UserType.valueOf(rs.getString("user_type")),
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
        "SELECT * FROM organizer_user_view WHERE user_type = 'organizer' AND organizer_id = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(
            new Organizer(
                rs.getInt("organizer_id"),
                rs.getString("organizer_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                UserType.valueOf(rs.getString("user_type")),
                rs.getString("company")));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.empty();
  }
}
