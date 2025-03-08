package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.repository.dao.EventDao;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EventService {
  private final EventDao eventDao;

  public List<Event> findAllEvents(int page, int size) {
    return eventDao.getAll(page, size);
  }

  public Optional<Event> findEventById(int id) {
    return eventDao.getById(id);
  }

  public void deleteEventById(int id) {
    eventDao.deleteById(id);
  }

  public void updateEvent(Event event) {
    eventDao.update(event);
  }

  public void createEvent(Event event) {
    eventDao.create(event);
  }
}
