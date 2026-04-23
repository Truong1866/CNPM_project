package models;

import java.time.LocalDate;

public class PhiBienLai {
    private String maPhiBienLai;
    private String maHo;
    private String maKhoanThu;
    private int soTienThuc;
    private LocalDate ngayThu;
    private String trangThaiThu;  // Đã thu / Hủy
    private String nguoiThu;
    private String ghiChu;

    // Constructor không tham số
    public PhiBienLai() {}

    // Constructor đầy đủ tham số
    public PhiBienLai(String maPhiBienLai, String maHo, String maKhoanThu, int soTienThuc,
                      LocalDate ngayThu, String trangThaiThu, String nguoiThu, String ghiChu) {
        this.maPhiBienLai = maPhiBienLai;
        this.maHo = maHo;
        this.maKhoanThu = maKhoanThu;
        this.soTienThuc = soTienThuc;
        this.ngayThu = ngayThu;
        this.trangThaiThu = trangThaiThu;
        this.nguoiThu = nguoiThu;
        this.ghiChu = ghiChu;
    }

    // Getters
    public String getMaPhiBienLai() {
        return maPhiBienLai;
    }

    public String getMaHo() {
        return maHo;
    }

    public String getMaKhoanThu() {
        return maKhoanThu;
    }

    public int getSoTienThuc() {
        return soTienThuc;
    }

    public LocalDate getNgayThu() {
        return ngayThu;
    }

    public String getTrangThaiThu() {
        return trangThaiThu;
    }

    public String getNguoiThu() {
        return nguoiThu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    // Setters
    public void setMaPhiBienLai(String maPhiBienLai) {
        this.maPhiBienLai = maPhiBienLai;
    }

    public void setMaHo(String maHo) {
        this.maHo = maHo;
    }

    public void setMaKhoanThu(String maKhoanThu) {
        this.maKhoanThu = maKhoanThu;
    }

    public void setSoTienThuc(int soTienThuc) {
        this.soTienThuc = soTienThuc;
    }

    public void setNgayThu(LocalDate ngayThu) {
        this.ngayThu = ngayThu;
    }

    public void setTrangThaiThu(String trangThaiThu) {
        this.trangThaiThu = trangThaiThu;
    }

    public void setNguoiThu(String nguoiThu) {
        this.nguoiThu = nguoiThu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}

