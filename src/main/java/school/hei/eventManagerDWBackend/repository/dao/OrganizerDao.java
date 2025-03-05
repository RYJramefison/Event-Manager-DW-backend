package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganizerDao implements CrudOperation<Organizer> {

  private final DataSource dataSource = new DataSource();

  @Override
  public void create(Organizer entity) {

  }

  @Override
  public void update(Organizer entity) {}

  @Override
  public void delete(Organizer entity) {}

  @Override
  public List<Organizer> getAll(int page, int size) {
    List<Organizer> organizers = new ArrayList<>();
      String sql = "SELECT o.id , u.name, u.email, u.password, u.registration_date, o.company FROM" +
              " organizer o INNER JOIN \"User\" u ON u.id = o.user_id LIMIT ? OFFSET ?";
      int offset = size * (page - 1);
      try (Connection connection = dataSource.getConnection();
           PreparedStatement pstm = connection.prepareStatement(sql)){
              pstm.setInt(1, size);
              pstm.setInt(2, offset);

           try (ResultSet res = pstm.executeQuery()){
             while (res.next()){
                Organizer organizer = new Organizer(
                        res.getInt("id"),
                        res.getString("name"),
                        res.getString("email"),
                        res.getString("password"),
                        res.getTimestamp("registration_date").toLocalDateTime(),
                        res.getString("company")
                );
                organizers.add(organizer);
             }
           }
      } catch (SQLException e){
        throw new RuntimeException("Not implemented", e);
      }
      return organizers;
  }

  @Override
  public Organizer getById(int id) {
    String sql =
        "SELECT o.id AS event_id,\n"
            + "       u.name,\n"
            + "       u.email,\n"
            + "       u.password,\n"
            + "       u.registration_date,\n"
            + "       o.company\n"
            + "FROM organizer o\n"
            + "         JOIN \"User\" u ON o.user_id = u.id\n"
            + "WHERE o.id = ?;";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement pr = connection.prepareStatement(sql)) {
      pr.setInt(1, id);
      ResultSet rs = pr.executeQuery();
      if (rs.next()) {
        return
            new Organizer(
                rs.getInt("event_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("registration_date").toLocalDateTime(),
                rs.getString("company"));
      }

    } catch (SQLException e) {
      throw new RuntimeException("Not implemented", e);
    }
      return null;
  }
}
