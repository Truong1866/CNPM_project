package models;

public class HoKhau {
    private String maHo;
    private String maCanHo;

    public String getHo() {
        return maHo;
    }

    public String getMaCanHo() {
        return maCanHo;
    }

    public HoKhau(){}

    public HoKhau(String maHo, String maCanHo){
        this.maHo = maHo;
        this.maCanHo = maCanHo;
    }
}
