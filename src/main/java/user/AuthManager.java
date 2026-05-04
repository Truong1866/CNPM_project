package user;

public class AuthManager {
    private static SessionUser currentUser = null;
    public static void login(SessionUser user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static SessionUser getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static boolean hasManagerRole(){
        if(currentUser.getRole().equals("ADMIN")){return true;}
        return currentUser.getRole().contains("MANAGER");
    }

    public static boolean hasAdminRole(){
        return currentUser.getRole().equals("ADMIN");
    }

    public static boolean hasResidentRole(){
        if(currentUser.getRole().equals("ADMIN")){return true;}
        return currentUser.getRole().contains("RESIDENT");
    }

    public static boolean hasFinanceRole(){
        if(currentUser.getRole().equals("ADMIN")){return true;}
        return currentUser.getRole().contains("FINANCE");
    }

}
