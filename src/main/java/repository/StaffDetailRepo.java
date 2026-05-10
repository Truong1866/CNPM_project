package repository;

import database.DB_manager;
import models.Staff;
import models.StaffDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StaffDetailRepo {

    public StaffDetail findByStaffId(String staffId) {
        try (Session session = DB_manager.getFactory().openSession()) {
            Staff staff = session.get(Staff.class, staffId);
            if (staff == null) return null;
            return session.get(StaffDetail.class, staff);
        }
    }

    public void updateDetail(StaffDetail detail) {
        Transaction tx = null;
        try (Session session = DB_manager.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(detail);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void changePassword(Staff staff, String newPassword) {
        Transaction tx = null;
        try (Session session = DB_manager.getFactory().openSession()) {
            tx = session.beginTransaction();
            Staff managed = session.merge(staff);
            managed.setPassword(newPassword);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
