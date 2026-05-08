package repository;

import database.DB_manager;
import models.Resident;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class ResidentRepo {
    public List<Resident> findAll() {
        try(Session session = DB_manager.getFactory().openSession()) {
            return session.createQuery("From Resident where deleteAt Is NOT NUll", Resident.class).list();
        }
    }

    public Resident findById(String residentId){
        try(Session session = DB_manager.getFactory().openSession()) {
            return  session.get(Resident.class, residentId);
        }
    }

    public List<Resident> findWithName(String name) {
        try(Session session = DB_manager.getFactory().openSession()) {
            String search = "%"+name+"%";
            var query = session.createQuery("From Resident where name ilike :name and deleteAt is not null", Resident.class);
            query.setParameter("name", search);
            return query.list();
        }
    }

    public List<Resident> findWithNumber(String number) {
        try(Session session = DB_manager.getFactory().openSession()) {
            String search = "%"+number+"%";
            var query = session.createQuery("From Resident where (residentId ilike :info " +
                    "or telephone ilike :info) and deleteAt is not null", Resident.class);
            return query.setParameter("info", search).list();
        }
    }

    public List<Resident> findByDate(LocalDate date) {
        try(Session session = DB_manager.getFactory().openSession()) {
            var query = session.createQuery("From Resident where birthday = :date and deleteAt is not null", Resident.class);
            query.setParameter("date", date);
            return query.list();
        }
    }

    public void addResident(Resident resident) {
        Transaction transaction = null;
        try(Session session = DB_manager.getFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(resident);
            transaction.commit();
        } catch(Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void deleteResident(Resident resident) {
        Transaction transaction = null;
        try(Session session = DB_manager.getFactory().openSession()) {
            transaction = session.beginTransaction();
            resident.setDeleteAt(Instant.now());
            transaction.commit();
        }catch(Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }
}
