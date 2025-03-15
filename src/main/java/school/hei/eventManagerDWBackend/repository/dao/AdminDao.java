package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.entity.UserType;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminDao implements CrudOperation<Admin> {
  private final DataSource dataSource = new DataSource();

  @Override
  public void create(Admin admin) {
    String insertUserSql = "INSERT INTO \"User\" (name, email, password, user_type) VALUES (?, ?, ?, ?::user_type_enum) RETURNING id";
    String insertAdminSql = "INSERT INTO Admin (user_id, admin_name) VALUES (?, ?)";

    try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);  // Début de la transaction

      try (PreparedStatement psUser = conn.prepareStatement(insertUserSql)) {
        psUser.setString(1, admin.getName());
        psUser.setString(2, admin.getEmail());
        psUser.setString(3, admin.getPassword());
        psUser.setString(4, admin.getUserType().name()); // Ajout de l'`user_type`

        ResultSet rs = psUser.executeQuery();
        if (rs.next()) {
          int userId = rs.getInt(1);  // Récupère l'ID de l'utilisateur créé

          try (PreparedStatement psAdmin = conn.prepareStatement(insertAdminSql)) {
            psAdmin.setInt(1, userId);
            psAdmin.setString(2, admin.getName());  // Nom de l'admin
            psAdmin.executeUpdate();
          }

          conn.commit();  // Validation de la transaction
        } else {
          throw new SQLException("Erreur lors de l'insertion de l'utilisateur, aucun ID retourné.");
        }
      } catch (SQLException e) {
        conn.rollback();  // Annulation de la transaction en cas d'erreur
        System.err.println("Erreur lors de la création de l'admin : " + e.getMessage());
        throw e;
      }
    } catch (SQLException e) {
      System.err.println("Erreur lors de la connexion ou de l'exécution de la requête : " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Admin entity) {
    String sql = "UPDATE \"User\" SET name = ?, password = ? WHERE user_type = 'admin' AND id = ?";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, entity.getName());
      ps.setString(2, entity.getPassword());
      ps.setInt(3, entity.getId());
      int rowsAffected = ps.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Aucun utilisateur trouvé avec l'ID: " + entity.getId());
      }
    } catch (SQLException e) {
      System.err.println("Error updating admin: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public void deleteById(int id) {
    String sql = "DELETE FROM admin WHERE id = ?";
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
  public List<Admin> getAll(int page, int size) {
    List<Admin> admins = new ArrayList<>();
    String sql = "SELECT * FROM admin_user_view WHERE user_type='admin' LIMIT ? OFFSET ?;";

    try (Connection conn = dataSource.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, size);
      ps.setInt(2, page * size);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Admin admin = new Admin(
                rs.getInt("admin_id"),
                rs.getString("admin_name"),
                rs.getString("email"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                UserType.valueOf(rs.getString("user_type"))
        );
        admins.add(admin);
      }
    } catch (SQLException e) {
      System.err.println("Error retrieving all admins: " + e.getMessage());
      throw new RuntimeException(e);
    }
    return admins;
  }


  public List<Admin> filter(String criteria){
    List<Admin> admins = new ArrayList<>();
    String sql =
        "SELECT admin_id, admin_name, email, registration_date, user_type\n"
            + "FROM admin_user_view WHERE user_type = 'admin' AND admin_name ILIKE '%i%'; ";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement pstm = connection.prepareStatement(sql)) {
         pstm.setString(1,'%' + criteria + '%');

         try (ResultSet res = pstm.executeQuery()){
           while (res.next()) {
             Admin admin = new Admin(
                     res.getInt("admin_id"),
                     res.getString("admin_name"),
                     res.getString("email"),
                     res.getTimestamp("registration_date").toLocalDateTime(),
                     UserType.valueOf(res.getString("user_type")));
             admins.add(admin);
           }
           return admins;
         }
    } catch (SQLException e) {
      throw new RuntimeException("filter admin not implemented", e);
    }
  }

  @Override
  public Optional<Admin> getById(int id) {
    String sql =
        "SELECT * FROM admin_user_view WHERE user_type = 'admin' AND admin_id = ?;";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        Admin admin =
            new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("admin_name"),
                    rs.getString("email"),
                    rs.getTimestamp("registration_date").toLocalDateTime(),
                    UserType.valueOf(rs.getString("user_type")));
        return Optional.of(admin);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Error retrieving admin by ID", e);
    }
    return Optional.empty();
  }
}
