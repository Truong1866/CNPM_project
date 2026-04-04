package moduls;

public class HoKhau_KhoanThu {
    private String maHo;
    private String maKhoanThu;
    private boolean trangThai;
    private int soLuong;
    private String ngayNop;
    private String hanNop;
    private String moTa;

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
}
