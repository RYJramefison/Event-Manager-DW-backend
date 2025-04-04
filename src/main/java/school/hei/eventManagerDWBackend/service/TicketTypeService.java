package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.TicketType;
import school.hei.eventManagerDWBackend.repository.dao.TicketTypeDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TicketTypeService {
    private final TicketTypeDao ticketTypeDao;

    public List<TicketType> getAll(int page, int size) throws Exception {
        return ticketTypeDao.getAllTicketTypes(page, size);
    }

    public void save(TicketType ticket) throws Exception {
        ticketTypeDao.createTicketType(ticket);
    }

    public Optional<TicketType> findById(int id) throws Exception {

        return ticketTypeDao.findById(id);
    }

    public List<TicketType> findByEventId(int id)throws Exception{
        return ticketTypeDao.findByEventId(id);
    }


    public List<TicketType> getAvailableTicketTypesForEvent(int eventId) throws SQLException {
        return ticketTypeDao.findByEventId(eventId).stream()
                .filter(TicketType::isAvailable)
                .collect(Collectors.toList());
    }
}