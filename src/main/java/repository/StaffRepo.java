package repository;

import database.DB_manager;
import models.Staff;
import org.hibernate.Session;

public class StaffRepo {
    public Staff findByStaffId(String staffId) {
        try(Session session = DB_manager.getFactory().openSession()) {
            return session.get(Staff.class, staffId);
        }
    }
}
