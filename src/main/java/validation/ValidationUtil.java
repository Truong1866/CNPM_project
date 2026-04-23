package validation;
import java.time.LocalDate;
public class ValidationUtil {
    public static boolean isValidAmount(int amount) {
        return amount > 0 && amount <= 999999999;
    }
    public static boolean isValidDate(LocalDate date) {
        return !date.isAfter(LocalDate.now());
    }
    public static String formatCurrency(long amount) {
        return String.format("%,d ₫", amount);
    }
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    public static boolean isValidPhone(String phone) {
        return phone.matches("^\\d{10,11}$");
    }
    public static boolean isValidCCCD(String cccd) {
        return cccd.matches("^\\d{12}$");
    }
}
