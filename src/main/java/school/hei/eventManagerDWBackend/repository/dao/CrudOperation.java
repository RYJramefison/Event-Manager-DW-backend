package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Event;

import java.util.List;
import java.util.Optional;

public interface CrudOperation<T> {
  void create(T entity);

  void update(T entity);

  void delete(T entity);

  List<T> getAll(int page, int size);

  Optional<T> getById(int id);
}
