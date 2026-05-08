package repository;

import database.DB_manager;
import models.Staff;
import models.StaffDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.Instant;
import java.util.List;

public class StaffManagerRepo {

    /** Lấy tất cả nhân viên chưa bị xóa (delete_at IS NULL) */
    public List<Staff> findAll() {
        try (Session session = DB_manager.getFactory().openSession()) {
            return session.createQuery(
                "FROM Staff WHERE deleteAt IS NULL", Staff.class).list();
        }
    }

    public Staff findById(String staffId) {
        try (Session session = DB_manager.getFactory().openSession()) {
            return session.get(Staff.class, staffId);
        }
    }

    /** Tìm theo staffId (LIKE) */
    public List<Staff> findByIdLike(String keyword) {
        try (Session session = DB_manager.getFactory().openSession()) {
            var q = session.createQuery(
                "FROM Staff WHERE staffId ILIKE :kw AND deleteAt IS NULL", Staff.class);
            q.setParameter("kw", "%" + keyword + "%");
            return q.list();
        }
    }

    /** Tìm theo tên (join StaffDetail) */
    public List<Staff> findByName(String name) {
        try (Session session = DB_manager.getFactory().openSession()) {
            var q = session.createQuery(
                "SELECT s FROM Staff s JOIN StaffDetail sd ON sd.staff = s " +
                "WHERE sd.name ILIKE :name AND s.deleteAt IS NULL", Staff.class);
            q.setParameter("name", "%" + name + "%");
            return q.list();
        }
    }

    /** Tìm theo role */
    public List<Staff> findByRole(String role) {
        try (Session session = DB_manager.getFactory().openSession()) {
            var q = session.createQuery(
                "FROM Staff WHERE role ILIKE :role AND deleteAt IS NOT NULL", Staff.class);
            q.setParameter("role", "%" + role + "%");
            return q.list();
        }
    }

    /** Thêm nhân viên mới (Staff + StaffDetail trong cùng transaction) */
    public void addStaff(Staff staff, StaffDetail detail) {
        Transaction tx = null;
        try (Session session = DB_manager.getFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(staff);
            detail.setStaff(staff);
            session.persist(detail);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /** Đổi mật khẩu */
    public void updatePassword(Staff staff, String newPassword) {
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

    /** Đổi role */
    public void updateRole(Staff staff, String newRole) {
        Transaction tx = null;
        try (Session session = DB_manager.getFactory().openSession()) {
            tx = session.beginTransaction();
            Staff managed = session.merge(staff);
            managed.setRole(newRole);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    /** Xóa mềm */
    public void deleteStaff(Staff staff) {
        Transaction tx = null;
        try (Session session = DB_manager.getFactory().openSession()) {
            tx = session.beginTransaction();
            Staff managed = session.merge(staff);
            managed.setDeleteAt(Instant.now());
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
