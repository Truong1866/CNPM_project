package models;

public class HoKhau_KhoanThu {
    private String maHo;
    private String maKhoanThu;
    private boolean trangThai;
    private int soLuong;
    private String ngayNop;
    private String hanNop;
    private String moTa;
    private long tienCo;
    private long tienThieu;
    private String trangThaiChiTiet;
    private long soTienThuc;

    public String getTrangThaiChiTiet() {
        return this.trangThaiChiTiet;
    }
    public void setTrangThaiChiTiet(String trangThaiChiTiet) {
        this.trangThaiChiTiet = trangThaiChiTiet;
    }
    public void setSoTienThuc(long soTienThuc) {
        this.soTienThuc = soTienThuc;
    }
    public String getMaHo() {
        return maHo;
    }

    public String getMaKhoanThu() {
        return maKhoanThu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getNgayNop() {
        return ngayNop;
    }

    public String getHanNop() {
        return hanNop;
    }

    public String getMoTa() {
        return moTa;
    }

    public long getTienThieu() { return this.tienThieu;}
    public long getSoTienThuc() { return this.soTienThuc;}
    public long getTienCo() { return this.tienCo;}
    public void setTienCo(long tienCo) { this.tienCo = tienCo;}

    public HoKhau_KhoanThu(){}

    public HoKhau_KhoanThu(String maHo, String maKhoanThu, boolean trangThai, int soLuong, String ngayNop, String hanNop, String moTa){
        this.maHo = maHo;
        this.maKhoanThu = maKhoanThu;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.ngayNop = ngayNop;
        this.hanNop = hanNop;
        this.moTa = moTa;
    }

    public HoKhau_KhoanThu(String maHo, String maKhoanThu, boolean trangThai, int soLuong, String ngayNop, String hanNop, String moTa, long soTienThuc, long tienCo, long tienThieu, String trangThaiChiTiet){
        this.maHo = maHo;
        this.maKhoanThu = maKhoanThu;
        this.trangThai = trangThai;
        this.soLuong = soLuong;
        this.ngayNop = ngayNop;
        this.hanNop = hanNop;
        this.moTa = moTa;
        this.soTienThuc = soTienThuc;
        this.tienCo = tienCo;
        this.tienThieu = tienThieu;
        this.trangThaiChiTiet = trangThaiChiTiet;
    }
}
