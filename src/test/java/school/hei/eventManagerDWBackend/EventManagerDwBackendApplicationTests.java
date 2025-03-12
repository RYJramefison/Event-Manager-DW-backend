package school.hei.eventManagerDWBackend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import school.hei.eventManagerDWBackend.entity.Admin;
import school.hei.eventManagerDWBackend.repository.dao.AdminDao;
import school.hei.eventManagerDWBackend.repository.db.DataSource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class EventManagerDwBackendApplicationTests {
		DataSource db = new DataSource();
		AdminDao subjectAdmin = new AdminDao();
	@Test
	void read_one_admin_in_database() {
		Admin alice = new Admin(1,"Alice Johnson","alice.johnson@example.com"
				,"password123", LocalDateTime.of(2025,3,5,7,25,37, 169836000), "Alice Admin");

		//Admin actual = subjectAdmin.getById(1);
		List<Admin> actual = subjectAdmin.getAll(1,2);

		Assertions.assertEquals(alice, actual);
	}

}
