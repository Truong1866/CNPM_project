package service;

import models.Staff;
import repository.StaffRepo;
import user.AuthManager;
import user.SessionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaffServices {
    private final StaffRepo staffRepo;
    private static final Logger logger = LoggerFactory.getLogger(StaffServices.class);

    public StaffServices(StaffRepo staffRepo) {
        this.staffRepo = staffRepo;
    }

    public Staff findByStaffId(String staffId) throws Exception {
        if (staffId == null) {
            return null;
        }
        if (staffId.length() > 20) {
            return null;
        }
        return staffRepo.findByStaffId(staffId);
    }

    public boolean loginServices(String staffId, String password) throws Exception {
        if (staffId == null) {
            return false;
        }
        if (password == null) {
            return false;
        }
        try {
            Staff user = findByStaffId(staffId);
            if (user == null) {
                return false;
            }
            if (user.getPassword().equals(password)) {
                SessionUser sessionUser = new SessionUser(user.getStaffId(), user.getPassword(), user.getRole());
                AuthManager.login(sessionUser);
            }
            return true;
        } catch (Exception e) {
            logger.error("Lỗi khi thực hiện đăng nhập: {}", e.getMessage(), e);
            return false;
        }
    }
}