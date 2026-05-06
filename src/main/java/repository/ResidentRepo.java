package repository;

import database.DB_manager;
import models.Resident;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class ResidentRepo {
    public List<Resident> findAll() {
        try(Session session = DB_manager.getFactory().openSession()) {
            return session.createQuery("From Resident", Resident.class).list();
        }
    }

    public List<Resident> findWithName(String name) {
        try(Session session = DB_manager.getFactory().openSession()) {
            String search = "%"+name+"%";
            var query = session.createQuery("From Resident where name ilike :name", Resident.class);
            query.setParameter("name", search);
            return query.list();
        }
    }

    public List<Resident> findWithNumber(String number) {
        try(Session session = DB_manager.getFactory().openSession()) {
            String search = "%"+number+"%";
            var query = session.createQuery("From Resident where residentId ilike :info " +
                    "or telephone ilike :info", Resident.class);
            return query.setParameter("info", search).list();
        }
    }

    public List<Resident> findByDate(LocalDate date) {
        try(Session session = DB_manager.getFactory().openSession()) {
            var query = session.createQuery("From Resident where birthday = :date", Resident.class);
            query.setParameter("date", date);
            return query.list();
        }
    }



}
