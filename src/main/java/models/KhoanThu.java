package models;

public class KhoanThu {
    private String maKhoanThu;
    private String tenKhoanThu;
    private boolean batBuoc;
    private boolean coDinh;
    private int donGia;
    private String moTa;

    public String getMaKhoanThu() {
        return maKhoanThu;
    }

    public String getTenKhoanThu() {
        return tenKhoanThu;
    }

    public boolean isBatBuoc() {
        return batBuoc;
    }

    public boolean isCoDinh() {
        return coDinh;
    }

    public int getDonGia() {
        return donGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public KhoanThu(){}

    public KhoanThu(String maKhoanThu, String tenKhoanThu, boolean batBuoc, boolean coDinh, int donGia, String moTa){
        this.maKhoanThu = maKhoanThu;
        this.tenKhoanThu = tenKhoanThu;
        this.batBuoc = batBuoc;
        this.coDinh = coDinh;
        this.donGia = donGia;
        this.moTa = moTa;
    }
}

