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
        String sql = "INSERT INTO ticket (ticket_code, reservation_id, ticket_quantity, ticket_type_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ticket.getTicketCode());
            stmt.setInt(2, ticket.getReservationId());
            stmt.setInt(3, ticket.getTicketQuantity());
            stmt.setInt(4, ticket.getTicketTypeId());
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
        String sql = "UPDATE ticket SET ticket_code = ?, reservation_id = ?, ticket_quantity = ?, ticket_type_id = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ticket.getTicketCode());
            stmt.setInt(2, ticket.getReservationId());
            stmt.setInt(3, ticket.getTicketQuantity());
            stmt.setInt(4, ticket.getTicketTypeId());
            stmt.setInt(5, ticket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM Ticket WHERE id = ?";
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

    public List<Ticket> getByReservation(int id){
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT id, ticket_code, reservation_id, ticket_quantity, ticket_type_id FROM ticket where reservation_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getInt("id"),
                        rs.getString("ticket_code"),
                        rs.getInt("reservation_id"),
                        rs.getInt("ticket_quantity"),
                        rs.getInt("ticket_type_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    @Override
    public List<Ticket> getAll(int page, int size) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT id, ticket_code, reservation_id, ticket_quantity, ticket_type_id FROM ticket LIMIT ? OFFSET ?";
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
                        rs.getInt("ticket_quantity"),
                        rs.getInt("ticket_type_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public List<Ticket> filter(List<Criteria> criterias) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT id, ticket_code, reservation_id,ticket_quantity, ticket_type_id FROM ticket WHERE 1=1";

        for (Criteria criteria : criterias) {
            if ("ticketCode".equals(criteria.getColumn())){
                sql += " AND ticket_code ILIKE '%" + criteria.getValue() + "%'";
            }
            else if ("reservetionId".equals(criteria.getColumn())){
                sql += " AND reservation_id=" + criteria.getValue();
            }
            else if ("ticketTypeId".equals(criteria.getColumn())){
                sql += " AND ticket_type_id =" + criteria.getValue();
            }
        }

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getInt("id"),
                        rs.getString("ticket_code"),
                        rs.getInt("reservation_id"),
                        rs.getInt("ticket_quantity"),
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
        String sql = "SELECT id, ticket_code, reservation_id,ticket_quantity, ticket_type_id FROM ticket WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Ticket(
                        rs.getInt("id"),
                        rs.getString("ticket_code"),
                        rs.getInt("reservation_id"),
                        rs.getInt("ticket_quantity"),
                        rs.getInt("ticket_type_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
