package controller;

public class AuthController {

    public static boolean authMahasiswa(String nim, String password) {
        return nim.equals("123") && password.equals("123");
    }

    public static boolean authDosen(String nidn, String password) {
        return nidn.equals("dosen") && password.equals("dosen");
    }

    public static boolean authAdmin(String id, String password) {
        return id.equals("admin") && password.equals("admin");
    }

}
