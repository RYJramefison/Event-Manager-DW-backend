package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.User;
import school.hei.eventManagerDWBackend.entity.UserType;
import school.hei.eventManagerDWBackend.repository.db.DataSource;
import school.hei.eventManagerDWBackend.utils.PasswordEncoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao implements CrudOperation<User> {

    private final DataSource dataSource = new DataSource();

    @Override
    public void create(User user) {
        String hashedPassword = PasswordEncoder.encode(user.getPassword());

        String sql = "INSERT INTO \"User\" (name, email, password, user_type) VALUES (?,?,?,?::user_type_enum)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashedPassword);
            stmt.setString(4, user.getUserType().name());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, name, email, password, registration_date, user_type " +
                     "FROM \"User\" WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registration_date").toLocalDateTime(),
                        UserType.valueOf(rs.getString("user_type"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public int createAndGetId(User user) {
        String hashedPassword = PasswordEncoder.encode(user.getPassword());

        String sql = "INSERT INTO \"User\" (name, email, password, user_type) VALUES (?,?,?,?::user_type_enum)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashedPassword);
            stmt.setString(4, user.getUserType().name());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    user.setId(generatedId);
                    return generatedId;
                } else {
                    throw new RuntimeException("Aucun ID généré lors de l'insertion de l'utilisateur.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        String hashedPassword = PasswordEncoder.encode(user.getPassword());

    String sql = "UPDATE \"User\" SET name = ?, password = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, hashedPassword);
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePartial(User user) {
        // 1. Vérifier que l'utilisateur existe
        Optional<User> existingUser = getById(user.getId());
        if (existingUser.isEmpty()) {
            return false;
        }

        // 2. Préparer la requête dynamiquement
        List<String> updates = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (user.getName() != null) {
            updates.add("name = ?");
            params.add(user.getName());
        }

        if (user.getEmail() != null) {
            updates.add("email = ?");
            params.add(user.getEmail());
        }

        if (user.getPassword() != null && !user.getPassword().equals(existingUser.get().getPassword())) {
            updates.add("password = ?");
            params.add(PasswordEncoder.encode(user.getPassword()));
        }

        if (user.getUserType() != null) {
            updates.add("user_type = ?::user_type_enum");
            params.add(user.getUserType().name());
        }

        // 3. Si rien à mettre à jour
        if (updates.isEmpty()) {
            return false;
        }

        // 4. Construire la requête
        String sql = "UPDATE \"User\" SET " + String.join(", ", updates) + " WHERE id = ?";
        params.add(user.getId());

        // 5. Exécuter
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Échec de la mise à jour partielle de l'utilisateur ID: " + user.getId(), e);
        }
    }

    public void deleteById(int id) {
    String sql = "DELETE FROM \"User\" WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAll(int page, int size) {
        List<User> users = new ArrayList<>();
    String sql =
        "SELECT id, name, email, registration_date, password, user_type FROM \"User\" LIMIT ? OFFSET ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, size);
            stmt.setInt(2, page * size);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registration_date").toLocalDateTime(),
                        UserType.valueOf(rs.getString("user_type"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public List<User> filter(List<Criteria> criterias) {
        List<User> users = new ArrayList<>();
        String sql =
                "SELECT id, name, email, registration_date, password, user_type FROM \"User\" WHERE 1=1";

        for (Criteria criteria : criterias) {
            if ("name".equals(criteria.getColumn())) {
                sql += " and name ilike '%" + criteria.getValue().toString() + "%'";
            } else if ("registrationDate".equals(criteria.getColumn())) {
                sql += " and registration_date =" + criteria.getValue().toString();}
            else if ("registrationDateMin".equals(criteria.getColumn())) {
                sql += " and registration_date >=" + criteria.getValue().toString() ;}
            else if ("registrationDateMax".equals(criteria.getColumn())) {
                sql += " and registration_date <=" + criteria.getValue().toString();}
            else if ("email".equals(criteria.getColumn())) {
                sql += " and email ilike '%" + criteria.getValue().toString() + "%'";
            }else if ("userType".equals(criteria.getColumn())) {
                sql += " and user_type =" + criteria.getValue().toString() ;
            }
        }
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registration_date").toLocalDateTime(),
                        UserType.valueOf(rs.getString("user_type"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional<User> getById(int id) {
    String sql =
        "SELECT id, name, email, registration_date, password, user_type FROM \"User\" WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getTimestamp("registration_date").toLocalDateTime(),
                        UserType.valueOf(rs.getString("user_type"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<User> authenticate(String email, String password) {
        String sql = "SELECT id, name, email, password, registration_date, user_type " +
                     "FROM \"User\" WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (PasswordEncoder.verify(password, storedHash)) {
                    return Optional.of(new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            storedHash,
                            rs.getTimestamp("registration_date").toLocalDateTime(),
                            UserType.valueOf(rs.getString("user_type"))
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
