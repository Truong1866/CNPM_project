package services;

import models.Staff;
import models.StaffDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.StaffManagerRepo;
import user.AuthManager;

import java.util.List;

public class StaffManagerServices {

    private final StaffManagerRepo repo;
    private static final Logger logger = LoggerFactory.getLogger(StaffManagerServices.class);

    public StaffManagerServices(StaffManagerRepo repo) {
        this.repo = repo;
    }

    public List<Staff> findAll() {
        if (!AuthManager.hasAdminRole()) {
            logger.warn("Unauthorized findAll staff");
            return List.of();
        }
        return repo.findAll();
    }

    public List<Staff> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        // Thử tìm theo id trước, sau đó tên
        boolean isOnlyLetters = keyword.matches("^\\p{L}[\\p{L} ]*$");
        if (isOnlyLetters) {
            return repo.findByName(keyword);
        }
        return repo.findByIdLike(keyword);
    }

    public List<Staff> findByRole(String role) {
        return repo.findByRole(role);
    }

    /**
     * Thêm nhân viên mới.
     * @return true nếu thành công
     */
    public boolean addStaff(Staff staff, StaffDetail detail) {
        if (!AuthManager.hasAdminRole()) return false;
        if (repo.findById(staff.getStaffId()) != null) {
            logger.warn("Staff {} already exists", staff.getStaffId());
            return false;
        }
        try {
            repo.addStaff(staff, detail);
            return true;
        } catch (Exception e) {
            logger.error("addStaff failed: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean updatePassword(Staff staff, String newPassword) {
        if (!AuthManager.hasAdminRole()) return false;
        try {
            repo.updatePassword(staff, newPassword);
            return true;
        } catch (Exception e) {
            logger.error("updatePassword failed: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean updateRole(Staff staff, String newRole) {
        if (!AuthManager.hasAdminRole()) return false;
        try {
            repo.updateRole(staff, newRole);
            return true;
        } catch (Exception e) {
            logger.error("updateRole failed: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean deleteStaff(Staff staff) {
        if (!AuthManager.hasAdminRole()) return false;
        // Không cho tự xóa chính mình
        if (staff.getStaffId().equals(AuthManager.getCurrentUser().getUserId())) {
            logger.warn("Cannot delete current logged-in user");
            return false;
        }
        try {
            repo.deleteStaff(staff);
            return true;
        } catch (Exception e) {
            logger.error("deleteStaff failed: {}", e.getMessage(), e);
            return false;
        }
    }
}
