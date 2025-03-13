package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.entity.Client;
import school.hei.eventManagerDWBackend.repository.dao.ClientDao;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ClientService {
  private final ClientDao clientDao;

  public List<Client> findAllClients(int page, int size) {
    return clientDao.getAll(page, size);
  }

  public List<Client> filterClients(String criteria){
    return clientDao.filter(criteria);
  }

  public Optional<Client> findClientById(int id) {
    return clientDao.getById(id);
  }

  public void deleteClientById(int id) {
    clientDao.deleteById(id);
  }

  public void updateClient(Client client) {
    clientDao.update(client);
  }

  public void createClient(Client client) {
    clientDao.create(client);
  }
}
