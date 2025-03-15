package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class Main {
  public static void main(String[] args) {
//    EventDao eventDao = new EventDao();
//    OrganizerDao organizerDao = new OrganizerDao();
//    Optional<Organizer> orgOpt = organizerDao.getById(2);
//
//    System.out.println("L'événement d'id 1 est : " + eventDao.getById(1));
//    System.out.println(eventDao.getAll(0, 2));
//    if (orgOpt.isPresent()) {
//      Organizer org = orgOpt.get();
//      System.out.println("L'événement d'id 1 est : " + eventDao.getById(1));
//      System.out.println(eventDao.getAll(0, 2));
//
//      Event newEvent = new Event(
//              20,
//              org,
//              "title1",
//              "description1",
//              LocalDateTime.of(2024, 5, 25, 12, 12, 1), // Correction de la date
//              "location",
//              StatusEvent.DRAFT
//      );
//      eventDao.create(newEvent);
//    }

//    EventDao eventDao11 = new EventDao();
//    eventDao11.deleteById(11);
//
//    AdminDao adminDao = new AdminDao();
//    System.out.println("admin : " + adminDao.getById(2));
//    System.out.println(adminDao.getAll(0, 2));
//
//    OrganizerDao organizerDao = new OrganizerDao();
//    System.out.println("organizer : " + organizerDao.getById(2));
//    System.out.println(organizerDao.getAll(0, 5));
//
//    TicketDao ticketDao = new TicketDao();
//    System.out.println(ticketDao.getById(1));
//    System.out.println(ticketDao.getAll(0,2));
//
//    ReservationDao reservationDao = new ReservationDao();
//    System.out.println(reservationDao.getById(1));
//    System.out.println(reservationDao.getAll(0, 2));

//    AdminDao adminDao = new AdminDao();
//        adminDao.create(new Admin(21, "name", "email@mail","password", LocalDateTime.now(),
//     UserType.admin));

//    OrganizerDao organizerDao = new OrganizerDao();
//    organizerDao.create(new Organizer(12, "name", "1email@email", "pass", LocalDateTime.now(), UserType.organizer,"company"));
ClientDao clientDao = new ClientDao();
clientDao.update(new Client(1, "client", "mailjug@email", "passer", LocalDateTime.now(), UserType.client));
  }
}
