package models;

public class CanHo {
    private String maCanHo;
    private String soPhong;
    private int dienTich;
    private String moTa;

    public String getMaCanHo() {
        return maCanHo;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public int getDienTich() {
        return dienTich;
    }

    public String getMoTa() {
        return moTa;
    }

    public CanHo(){}

    public CanHo(String maCanHo, String soPhong, int dienTich, String moTa){
        this.maCanHo = maCanHo;
        this.soPhong = soPhong;
        this.dienTich = dienTich;
        this.moTa = moTa;
    }
}
