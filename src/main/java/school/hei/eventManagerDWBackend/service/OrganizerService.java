package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.repository.dao.Criteria;
import school.hei.eventManagerDWBackend.repository.dao.OrganizerDao;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrganizerService {
  private final OrganizerDao organizerDao;

  public List<Organizer> findAllOrganizers(int page, int size) {
    return organizerDao.getAll(page, size);
  }

  public List<Organizer> filterOrganizer(List<Criteria> criteria) {
    return organizerDao.filter(criteria);
  }

  public Optional<Organizer> findOrganizerById(int id) {
    return organizerDao.getById(id);
  }

  public void deleteOrganizerById(int id) {
    organizerDao.deleteById(id);
  }

  public void updateOrganizer(Organizer client) {
    organizerDao.update(client);
  }

  public void createOrganizer(Organizer client) {
    organizerDao.create(client);
  }
}
