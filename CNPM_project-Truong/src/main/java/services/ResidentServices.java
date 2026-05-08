package services;

import models.Resident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ResidentRepo;
import user.AuthManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private LocalDate convertStringToLocalDate(String date){
        Pattern datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
        Matcher dateMatcher = datePattern.matcher(date);
        if(dateMatcher.find()){
            String extractedDate = dateMatcher.group();
            logger.info("Found a localDate: {}", extractedDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate localDate = LocalDate.parse(extractedDate, formatter);
                logger.info("Convert date complete: {}", localDate);
                return localDate;
            }catch (DateTimeParseException e) {
                logger.error("Cannot convert to form of local date: {}", e.getMessage(), e);
            }
        }
        return null;
    }

    public List<Resident> findByContainInfo(String info){
        String infoLowerCase = info.toLowerCase();
        boolean isOnlyNumber = infoLowerCase.matches("^[0-9]+$");
        boolean isOnlyLetter =  infoLowerCase.matches("^\\p{L}+$");
        LocalDate localDate = convertStringToLocalDate(infoLowerCase);
        if(localDate != null){
            return residentRepo.findByDate(localDate);
        } else if (isOnlyNumber) {
            return residentRepo.findWithNumber(infoLowerCase);
        } else if (isOnlyLetter) {
            return residentRepo.findWithName(infoLowerCase);
        }
        return null;
    }

    public boolean addResident(Resident resident){
        if(residentRepo.findById(resident.getResidentId()) != null){
            try{
                residentRepo.addResident(resident);
                return true;
            }catch (Exception e){
                logger.error(e.getMessage());
                return false;
            }
        }return false;
    }

    public boolean deleteResident(Resident resident){
        if(residentRepo.findById(resident.getResidentId()) != null){
            try{
                residentRepo.deleteResident(resident);
                return true;
            }catch (Exception e){
                logger.error(e.getMessage());
                return false;
            }
        }
        return false;
    }
}
