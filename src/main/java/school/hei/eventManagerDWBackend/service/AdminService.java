package school.hei.eventManagerDWBackend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.repository.dao.AdminDao;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AdminService {
    private final AdminDao adminDao;

    public List<Admin> getAllAdmins(int page, int size) {
        return adminDao.getAll(page, size);
    }

    public Optional<Admin> getAdminById(int id) {
        return adminDao.getById(id);
    }

    public List<Admin> filterAdmin(String criteria){
        return adminDao.filter(criteria);
    }

    public void createAdmin(Admin admin) {
        adminDao.create(admin);
    }

    public void updateAdmin(Admin admin) {
        adminDao.update(admin);
    }

    public void deleteAdminById(int id) {
        adminDao.deleteById(id);
    }
}
