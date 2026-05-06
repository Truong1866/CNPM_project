package services;

import models.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ResidentRepo;
import user.AuthManager;

import java.time.LocalDate;
import java.util.List;

public class ResidentServices {
    private final ResidentRepo residentRepo;
    private static final Logger logger  = LoggerFactory.getLogger(ResidentServices.class);

    public ResidentServices(ResidentRepo residentRepo) {
        this.residentRepo = residentRepo;
    }

    public List<Resident> findAll() {
        if(AuthManager.isLoggedIn()) {
            logger.info("user has role for finding all Residents");
            return residentRepo.findAll();
        }
        logger.info("user doesn't have role for finding all Residents");
        return null;
    }

    public List<Resident> findByDate(LocalDate date) {
        return residentRepo.findByDate(date);
    }

    public List<Resident> findByName(String name) {
        return residentRepo.findWithName(name);
    }

    public List<Resident> findByNumber(String number) {
        return residentRepo.findWithNumber(number);
    }
}
