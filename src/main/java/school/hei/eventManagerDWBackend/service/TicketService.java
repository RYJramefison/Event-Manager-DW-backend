package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.Ticket;
import school.hei.eventManagerDWBackend.repository.dao.TicketDao;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TicketService {
  private final TicketDao ticketDao;

  public List<Ticket> findAllTickets(int page, int size) {
    return ticketDao.getAll(page, size);
  }

  public Optional<Ticket> findTicketById(int id) {
    return ticketDao.getById(id);
  }

  public void deleteTicketById(int id) {
    ticketDao.deleteById(id);
  }

  public void updateTicket(Ticket ticket) {
    ticketDao.update(ticket);
  }

  public void createTicket(Ticket ticket) {
    ticketDao.create(ticket);
  }
}
