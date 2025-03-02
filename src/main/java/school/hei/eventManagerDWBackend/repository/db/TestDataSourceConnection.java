package school.hei.eventManagerDWBackend.repository.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDataSourceConnection {
  public static void main(String[] args) {
    DataSource dataSource = new DataSource();
    try (Connection con = dataSource.getConnection()) {
      System.out.println("You have successfully connected to database");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
