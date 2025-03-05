package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.entity.Reservation;
import school.hei.eventManagerDWBackend.entity.StatusReservation;

import java.time.LocalDateTime;
import java.util.Optional;

public class Main {
  public static void main(String[] args) {
//    EventDao eventDao = new EventDao();
//    System.out.println("L'événement d'id 1 est : " + eventDao.getById(1));
//    System.out.println(eventDao.getAll(0, 2));
//
//    EventDao eventDao11 = new EventDao();
//    eventDao11.deleteById(11);

//    AdminDao adminDao = new AdminDao();
//    System.out.println("admin : " + adminDao.getById(2));
//    System.out.println(adminDao.getAll(0, 2));

    OrganizerDao organizerDao = new OrganizerDao();
    System.out.println("organizer : " + organizerDao.getById(2));
    System.out.println(organizerDao.getAll(0, 5));

    TicketDao ticketDao = new TicketDao();
    System.out.println(ticketDao.getById(1));
    System.out.println(ticketDao.getAll(0,2));

    ReservationDao reservationDao = new ReservationDao();
    System.out.println(reservationDao.getById(1));
    System.out.println(reservationDao.getAll(0, 2));

    ClientDao clientDao = new ClientDao();
    System.out.println(clientDao.getAll(0, 4));
  }
}
