package school.hei.eventManagerDWBackend.service;

import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.User;
import school.hei.eventManagerDWBackend.repository.dao.UserDao;

import java.util.Optional;

@Service
public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> login(String email, String password) {
        return userDao.authenticate(email, password);
    }
}