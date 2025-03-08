package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.Reservation;
import school.hei.eventManagerDWBackend.repository.dao.ReservationDao;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReservationService {
  private final ReservationDao reservationDao;

  public List<Reservation> findAllReservations(int page, int size) {
    return reservationDao.getAll(page, size);
  }

  public Optional<Reservation> findReservationById(int id) {
    return reservationDao.getById(id);
  }

  public void deleteReservationById(int id) {
    reservationDao.deleteById(id);
  }

  public void updateReservation(Reservation reservation) {
    reservationDao.update(reservation);
  }

  public void createReservation(Reservation reservation) {
    reservationDao.create(reservation);
  }
}
