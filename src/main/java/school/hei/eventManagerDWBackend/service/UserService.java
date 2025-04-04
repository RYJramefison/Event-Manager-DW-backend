package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.User;
import school.hei.eventManagerDWBackend.repository.dao.Criteria;
import school.hei.eventManagerDWBackend.repository.dao.UserDao;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserDao userDao;

    public List<User> getAllUsers(int page, int size) {
        return userDao.getAll(page, size);
    }

    public List<User> filter(List<Criteria>  criteria) {
        return userDao.filter(criteria);
    }

    public Optional<User> getUserById(int id) {
        return userDao.getById(id);
    }

    public void createUser(User user) {
        userDao.create(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUserById(int id) {
        userDao.deleteById(id);
    }

    public Optional<User> login(String email, String password) {
        return userDao.authenticate(email, password);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userDao.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
