package school.hei.eventManagerDWBackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.*;
import school.hei.eventManagerDWBackend.repository.dao.AdminDao;
import school.hei.eventManagerDWBackend.repository.dao.ClientDao;
import school.hei.eventManagerDWBackend.repository.dao.OrganizerDao;
import school.hei.eventManagerDWBackend.repository.dao.UserDao;
import school.hei.eventManagerDWBackend.utils.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final AdminDao adminDao;
    private final OrganizerDao organizerDao;
    private final ClientDao clientDao;
    private PasswordEncoder passwordEncoder;


    public Optional<User> login(String email, String password) {
        return userDao.authenticate(email, password);
    }

    public boolean emailExists(String email) {
        return userDao.findByEmail(email).isPresent();
    }



    public Admin registerAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminDao.save(admin);
    }

    public Organizer registerOrganizer(Organizer organizer) {
        organizer.setPassword(passwordEncoder.encode(organizer.getPassword()));
        return organizerDao.save(organizer);
    }

    public Client registerClient(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientDao.save(client);
    }

//    public Admin registerAdmin(String name, String email, String hashedPassword, String adminName) {
//        Admin admin = new Admin();
//        admin.setName(name);
//        admin.setEmail(email);
//        admin.setPassword(hashedPassword);
//        admin.setUserType(UserType.admin);
//        admin.setName(adminName);
//        return adminDao.create(admin);
//    }
//
//    public Organizer registerOrganizer(String name, String email, String hashedPassword, String company) {
//        Organizer organizer = new Organizer();
//        organizer.setName(name);
//        organizer.setEmail(email);
//        organizer.setPassword(hashedPassword);
//        organizer.setUserType(UserType.organizer);
//        organizer.setCompany(company);
//        return organizerDao.create(organizer);
//    }
//
//    public Client registerClient(String name, String email, String hashedPassword) {
//        Client client = new Client();
//        client.setName(name);
//        client.setEmail(email);
//        client.setPassword(hashedPassword);
//        client.setUserType(UserType.organizer);
//        return clientDao.create(client);
//    }
}