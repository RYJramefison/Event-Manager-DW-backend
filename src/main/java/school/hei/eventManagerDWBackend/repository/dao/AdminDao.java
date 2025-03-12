package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.Admin;
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
  public void create(Admin entity) {
    String sql = "INSERT INTO admin (admin_name, user_id) VALUES (?, ?)";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, entity.getAdminName());
      ps.setInt(2, entity.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Error creating admin: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Admin entity) {
    String sql = "UPDATE admin SET admin_name = ? WHERE id = ?";
    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, entity.getAdminName());
      ps.setInt(2, entity.getId());
      ps.executeUpdate();
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
    String sql =
        "SELECT * FROM admin_user_view LIMIT ? OFFSET ?;";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, size);
      ps.setInt(2, page * size);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        Admin admin =
            new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("admin_name"),
                    rs.getString("email"),
                    rs.getTimestamp("registration_date").toLocalDateTime());
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
    String sql = "select admin_id, admin_name, email, registration_date from admin_user_view WHERE admin_name ILIKE ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement pstm = connection.prepareStatement(sql)) {
         pstm.setString(1,'%' + criteria + '%');

         try (ResultSet res = pstm.executeQuery()){
           while (res.next()) {
             Admin admin = new Admin(
                     res.getInt("admin_id"),
                     res.getString("admin_name"),
                     res.getString("email"),
                     res.getTimestamp("registration_date").toLocalDateTime());
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
        "SELECT * FROM admin_user_view WHERE admin_id = ?;";

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
                rs.getTimestamp("registration_date").toLocalDateTime());
        return Optional.of(admin);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Error retrieving admin by ID", e);
    }
    return Optional.empty();
  }
}
