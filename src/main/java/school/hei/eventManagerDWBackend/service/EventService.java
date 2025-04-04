package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.hei.eventManagerDWBackend.cloudinary.response.CloudinaryResponse;
import school.hei.eventManagerDWBackend.cloudinary.service.CloudinaryService;
import school.hei.eventManagerDWBackend.cloudinary.util.FileUploadUtil;
import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.repository.dao.Criteria;
import school.hei.eventManagerDWBackend.repository.dao.EventDao;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EventService {
  private final EventDao eventDao;

  @Autowired
  private CloudinaryService cloudinaryService;

  public List<Event> findAllEvents(int page, int size) {
    return eventDao.getAll(page, size);
  }

  public List<Event> filterEvent(List<Criteria> criterias) {
    return eventDao.filter(criterias);
  }

  public Integer getLastInsertId(){
    return eventDao.getLastInsertedId();
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

  // Modification 1: Création d'événement avec image optionnelle
  public Event createEvent(Event event, MultipartFile imageFile) {
    // Créer l'événement d'abord avec imageUrl null
    event.setImageUrl(null);
    eventDao.create(event);

    // Si une image est fournie, l'uploader
    if (imageFile != null && !imageFile.isEmpty()) {
      upload(event.getId(), imageFile);
      // Recharger l'événement pour avoir la version mise à jour
      return eventDao.getById(event.getId())
              .orElseThrow(() -> new RuntimeException("Event not found after creation"));
    }

    return event;
  }

  // Modification 2: Garder l'ancienne méthode pour compatibilité
  public void createEvent(Event event) {
    createEvent(event, null);
  }

  // Modification 3: Amélioration de la méthode upload
  public Event upload(final int id, final MultipartFile file) {
    final Event event = this.eventDao.getById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
    FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);
    final String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
    final CloudinaryResponse response = this.cloudinaryService.uploadFile(file, fileName);

    if (response == null || response.getUrl() == null) {
      throw new RuntimeException("Failed to upload image to Cloudinary");
    }

    event.setImageUrl(response.getUrl());
    this.eventDao.update(event);
    return event;
  }

  // Ajoutez cette méthode dans EventService
  public List<Event> getLast6Events() {
    // Implémentation avec EventDao (à adapter selon votre implémentation de EventDao)
    return eventDao.getAll(0, Integer.MAX_VALUE).stream()
            .sorted(Comparator.comparing(Event::getDateEvent).reversed())
            .limit(6)
            .collect(Collectors.toList());
  }

  public List<Event> getEventsByOrganizerId(int organizerId) {
    return eventDao.findByOrganizerId(organizerId);
  }
}
