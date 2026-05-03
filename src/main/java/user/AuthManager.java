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

    public static boolean hasAnyRole(String... roles){
        if(curentUser == null) return false;
        if(curentUser.getRole().equals("ADMIN")) return true;
        for (String role : roles){
            if(curentUser.getRole().equals(role)) return true;
        }
        return false;
    }
}
