package school.hei.eventManagerDWBackend.repository.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import school.hei.eventManagerDWBackend.cloudinary.response.CloudinaryResponse;
import school.hei.eventManagerDWBackend.cloudinary.service.CloudinaryService;
import school.hei.eventManagerDWBackend.cloudinary.util.FileUploadUtil;
import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.entity.Organizer;
import school.hei.eventManagerDWBackend.entity.StatusEvent;
import school.hei.eventManagerDWBackend.entity.UserType;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EventDao implements CrudOperation<Event> {
  private final DataSource dataSource = new DataSource();
  private CloudinaryService cloudinaryService;

  public void uploadImage(int id, MultipartFile file) {
    Event event = this.getById(id)
            .orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));

    FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);

    String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());

    CloudinaryResponse response = this.cloudinaryService.uploadFile(file, fileName);  // ✅ Correction ici
    if (response == null || response.getUrl() == null) {
      throw new RuntimeException("Image upload failed");
    }

    event.setImageUrl(response.getUrl());

    this.update(event);
  }

  @Override
  public void create(Event event) {
    String sql =
        "INSERT INTO event (organizer_id, title, description, event_date, location, status, image_Url) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, event.getOrganizer().getId());
      stmt.setString(2, event.getTitle());
      stmt.setString(3, event.getDescription());
      stmt.setTimestamp(4, Timestamp.valueOf(event.getDateEvent()));
      stmt.setString(5, event.getLocation());
      stmt.setString(6, event.getStatus().name());
      stmt.setString(7, event.getImageUrl());  // Initialement null, sera mis à jour après l'upload
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Event createEventWithImage(Event event, MultipartFile imageFile) {
    event.setImageUrl(null);
    create(event);

    if (imageFile != null && !imageFile.isEmpty()) {
      uploadImage(event.getId(), imageFile);
      return getById(event.getId()).orElse(event);
    }

    return event;
  }

  @Override
  public void update(Event event) {
    String sql =
            "UPDATE event SET organizer_id = ?, title = ?, description = ?, event_date = ?, location = ?, status = ?, image_url = ? WHERE id = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, event.getOrganizer().getId());
      stmt.setString(2, event.getTitle());
      stmt.setString(3, event.getDescription());
      stmt.setTimestamp(4, Timestamp.valueOf(event.getDateEvent()));
      stmt.setString(5, event.getLocation());
      stmt.setString(6, event.getStatus().name());
      stmt.setString(7, event.getImageUrl());  // Ajout de l'image
      stmt.setInt(8, event.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void deleteById(int id) {
    String sql = "DELETE FROM event WHERE id = ?";
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

  @Override
  public List<Event> getAll(int page, int size) {
    List<Event> events = new ArrayList<>();
    String sql = "SELECT e.id AS event_id, o.id AS organizer_id, o.company, e.title, e.description, "
            + "e.event_date, e.location, e.status, u.id AS user_id, u.name, u.email, u.password, "
            + "u.registration_date, u.user_type, e.image_url "
            + "FROM event e "
            + "INNER JOIN organizer o ON e.organizer_id = o.id "
            + "INNER JOIN \"User\" u ON o.user_id = u.id "
            + "LIMIT ? OFFSET ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, size);
      stmt.setInt(2, page * size);
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          Organizer organizer = new Organizer(
                  rs.getInt("organizer_id"),
                  rs.getString("name"),
                  rs.getString("email"),
                  rs.getString("password"),
                  rs.getTimestamp("registration_date").toLocalDateTime(),
                  UserType.valueOf(rs.getString("user_type")),
                  rs.getString("company")
          );
          Event event = new Event(
                  rs.getInt("event_id"),
                  organizer,
                  rs.getString("title"),
                  rs.getString("description"),
                  rs.getTimestamp("event_date").toLocalDateTime(),
                  rs.getString("location"),
                  StatusEvent.valueOf(rs.getString("status")),
                  rs.getString("image_url")
          );
          events.add(event);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error retrieving events", e);
    }
    return events;
  }

  public List<Event> filter(List<Criteria> criterias) {
    List<Event> events = new ArrayList<>();
    StringBuilder sql = new StringBuilder(
            "SELECT e.id AS event_id, o.id AS organizer_id, o.company, e.title, e.description, "
                    + "e.event_date, e.location, e.status, u.id AS user_id, u.name, u.email, u.password, "
                    + "u.registration_date, u.user_type, e.image_url FROM event e "
                    + "INNER JOIN organizer o ON e.organizer_id = o.id "
                    + "INNER JOIN \"User\" u ON o.user_id = u.id WHERE 1=1"
    );
    List<Object> parameters = new ArrayList<>();
    for (Criteria criteria : criterias) {
      switch (criteria.getColumn()) {
        case "company":
          sql.append(" AND o.company ILIKE ?");
          parameters.add("%" + criteria.getValue().toString() + "%");
          break;
        case "dateEvent":
          sql.append(" AND e.event_date = ?");
          parameters.add(criteria.getValue());
          break;
        case "dateEventMin":
          sql.append(" AND e.event_date >= ?");
          parameters.add(criteria.getValue());
          break;
        case "dateEventMax":
          sql.append(" AND e.event_date <= ?");
          parameters.add(criteria.getValue());
          break;
        case "status":
          sql.append(" AND e.status = ?");
          parameters.add(criteria.getValue());
          break;
        case "location":
          sql.append(" AND e.location ILIKE ?");
          parameters.add("%" + criteria.getValue().toString() + "%");
          break;
        case "title":
          sql.append(" AND e.title ILIKE ?");
          parameters.add("%" + criteria.getValue().toString() + "%");
          break;
      }
    }
    try (Connection connection = dataSource.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
      for (int i = 0; i < parameters.size(); i++) {
        stmt.setObject(i + 1, parameters.get(i));
      }
      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          Organizer organizer = new Organizer(
                  rs.getInt("organizer_id"),
                  rs.getString("name"),
                  rs.getString("email"),
                  rs.getString("password"),
                  rs.getTimestamp("registration_date").toLocalDateTime(),
                  UserType.valueOf(rs.getString("user_type")),
                  rs.getString("company")
          );
          Event event = new Event(
                  rs.getInt("event_id"),
                  organizer,
                  rs.getString("title"),
                  rs.getString("description"),
                  rs.getTimestamp("event_date").toLocalDateTime(),
                  rs.getString("location"),
                  StatusEvent.valueOf(rs.getString("status")),
                  rs.getString("image_url")
          );
          events.add(event);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error filtering events", e);
    }
    return events;
  }


  @Override
  public Optional<Event> getById(int id) {
    String sql = """
        SELECT e.id AS event_id, o.id AS organizer_id, o.company, 
               e.title, e.description, e.event_date, e.location, e.status,
               e.image_url,  -- Ajout de l'URL de l'image
               U.name, U.email, U.password, U.registration_date, U.user_type
        FROM event e
        INNER JOIN organizer o ON e.organizer_id = o.id
        INNER JOIN "User" U ON U.id = o.user_id
        WHERE e.id = ?;
    """;

    try (Connection connection = dataSource.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          Organizer organizer = new Organizer(
                  rs.getInt("organizer_id"),
                  rs.getString("name"),
                  rs.getString("email"),
                  rs.getString("password"),
                  rs.getTimestamp("registration_date").toLocalDateTime(),
                  UserType.valueOf(rs.getString("user_type")),
                  rs.getString("company")
          );

          Event event = new Event(
                  rs.getInt("event_id"),
                  organizer,
                  rs.getString("title"),
                  rs.getString("description"),
                  rs.getTimestamp("event_date").toLocalDateTime(),
                  rs.getString("location"),
                  StatusEvent.valueOf(rs.getString("status")),
                  rs.getString("image_url") // Ajout de l'URL de l'image
          );

          return Optional.of(event);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error retrieving event by ID", e);
    }

    return Optional.empty();
  }

  public List<Event> findByOrganizerId(int organizerId) {
    List<Event> events = new ArrayList<>();
    String sql = """
        SELECT e.id AS event_id, o.id AS organizer_id, o.company, e.title, e.description, 
               e.event_date, e.location, e.status, u.id AS user_id, u.name, u.email, u.password, 
               u.registration_date, u.user_type, e.image_url 
        FROM event e 
        INNER JOIN organizer o ON e.organizer_id = o.id 
        INNER JOIN "User" u ON o.user_id = u.id 
        WHERE o.id = ?""";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, organizerId);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          Organizer organizer = new Organizer(
                  rs.getInt("organizer_id"),
                  rs.getString("name"),
                  rs.getString("email"),
                  rs.getString("password"),
                  rs.getTimestamp("registration_date").toLocalDateTime(),
                  UserType.valueOf(rs.getString("user_type")),
                  rs.getString("company")
          );

          Event event = new Event(
                  rs.getInt("event_id"),
                  organizer,
                  rs.getString("title"),
                  rs.getString("description"),
                  rs.getTimestamp("event_date").toLocalDateTime(),
                  rs.getString("location"),
                  StatusEvent.valueOf(rs.getString("status")),
                  rs.getString("image_url")
          );
          events.add(event);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error finding events by organizer ID", e);
    }
    return events;
  }

}
