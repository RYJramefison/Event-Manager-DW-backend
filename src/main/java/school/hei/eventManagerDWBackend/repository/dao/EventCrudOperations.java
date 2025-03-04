package school.hei.eventManagerDWBackend.repository.dao;

import school.hei.eventManagerDWBackend.entity.Event;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.util.List;
import java.util.Optional;

public class EventCrudOperations implements CrudOperation<Event>{
    private DataSource dataSource = new DataSource();

    @Override
    public List<Event> getAll(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page must be greater than 0 but actual is " + page);
        }

        String sql = "";
        return List.of();
    }

    @Override
    public Optional<Event> getById(int id) {
        return Optional.empty();
    }


    @Override
    public void create(Event event) {

    }

    @Override
    public void update(Event entity) {

    }

    @Override
    public void delete(Event entity) {

    }


}
