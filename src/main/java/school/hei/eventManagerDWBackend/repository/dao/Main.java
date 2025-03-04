package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Admin;

import java.util.Optional;

public class Main {
  public static void main(String[] args) {
    EventDao eventDao = new EventDao();
    System.out.println("L'événement d'id 1 est : " + eventDao.getById(1));

    AdminDao adminDao = new AdminDao();
    System.out.println("admin : " + adminDao.getById(2));
  }
}
