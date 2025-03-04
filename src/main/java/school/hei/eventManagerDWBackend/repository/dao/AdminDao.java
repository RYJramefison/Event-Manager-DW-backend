package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void delete(Admin entity) {
        String sql = "DELETE FROM admin WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Admin> getAll(int page, int size) {
        List<Admin> admins = new ArrayList<>();
    String sql =
        "SELECT a.id AS admin_id, u.name, u.email, u.password, u.registration_date, a.admin_name\n"
            + "                     FROM admin a JOIN \"User\" u ON a.user_id = u.id\n"
            + "                     LIMIT ? OFFSET ?;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registration_date").toLocalDateTime(),
                        rs.getString("admin_name")
                );
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all admins: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return admins;
    }

    @Override
    public Optional<Admin> getById(int id) {
    String sql =
        "SELECT a.id AS admin_id,\n"
            + "                       u.name,\n"
            + "                       u.email,\n"
            + "                       u.password,\n"
            + "                       u.registration_date,\n"
            + "                       a.admin_name\n"
            + "                FROM admin a\n"
            + "                JOIN \"User\" u ON a.user_id = u.id\n"
            + "                WHERE a.id = ?;";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registration_date").toLocalDateTime(),
                        rs.getString("admin_name")
                );
                return Optional.of(admin);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving admin by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}