package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import school.hei.eventManagerDWBackend.entity.Ticket;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDao implements CrudOperation<Ticket> {

    private final DataSource dataSource = new DataSource();

    @Override
    public void create(Ticket ticket) {
        String sql = "INSERT INTO ticket (ticket_code, reservation_id, ticket_type_id) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ticket.getTicketCode());
            stmt.setInt(2, ticket.getReservationId());
            stmt.setInt(3, ticket.getTicketTypeId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                ticket.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Ticket ticket) {
        String sql = "UPDATE ticket SET ticket_code = ?, reservation_id = ?, ticket_type_id = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ticket.getTicketCode());
            stmt.setInt(2, ticket.getReservationId());
            stmt.setInt(3, ticket.getTicketTypeId());
            stmt.setInt(4, ticket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Ticket ticket) {
        String sql = "DELETE FROM ticket WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ticket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getAll(int page, int size) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT id, ticket_code, reservation_id, ticket_type_id FROM ticket LIMIT ? OFFSET ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, size);
            stmt.setInt(2, page * size);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getInt("id"),
                        rs.getString("ticket_code"),
                        rs.getInt("reservation_id"),
                        rs.getInt("ticket_type_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    @Override
    public Optional<Ticket> getById(int id) {
        String sql = "SELECT id, ticket_code, reservation_id, ticket_type_id FROM ticket WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Ticket(
                        rs.getInt("id"),
                        rs.getString("ticket_code"),
                        rs.getInt("reservation_id"),
                        rs.getInt("ticket_type_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
