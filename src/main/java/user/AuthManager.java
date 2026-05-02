package user;

public class AuthManager {
    private static SessionUser curentUser = null;
    public static void login(SessionUser user) {
        curentUser = user;
    }

    public static void logout() {
        curentUser = null;
    }

    public static SessionUser getCurrentUser() {
        return curentUser;
    }

    public static boolean isLoggedIn() {
        return curentUser != null;
    }

    public static boolean hasRole (String role){
        if(curentUser == null) return false;
        return curentUser.getRole().equals(role);
    }
}
