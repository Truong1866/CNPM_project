package models;
public class BaoCaoThongKe {
    private String maKhoanThu, tenKhoanThu;
    private int tongHoCoKhoan, hoNopDu;
    private long tongTienThu, tongTienThieu;
    private double tiLeNopDu;
    public BaoCaoThongKe() {}
    public BaoCaoThongKe(String ma, String ten, int hoCo, long tienThu, long tienThieu, int hoNop, double tiLe) {
        this.maKhoanThu = ma; this.tenKhoanThu = ten; this.tongHoCoKhoan = hoCo;
        this.tongTienThu = tienThu; this.tongTienThieu = tienThieu; this.hoNopDu = hoNop; this.tiLeNopDu = tiLe;
    }
    public String getMaKhoanThu() { return maKhoanThu; }
    public String getTenKhoanThu() { return tenKhoanThu; }
    public int getTongHoCoKhoan() { return tongHoCoKhoan; }
    public long getTongTienThu() { return tongTienThu; }
    public long getTongTienThieu() { return tongTienThieu; }
    public int getHoNopDu() { return hoNopDu; }
    public double getTiLeNopDu() { return tiLeNopDu; }
    public void setMaKhoanThu(String m) { this.maKhoanThu = m; }
    public void setTenKhoanThu(String t) { this.tenKhoanThu = t; }
    public void setTongHoCoKhoan(int h) { this.tongHoCoKhoan = h; }
    public void setTongTienThu(long t) { this.tongTienThu = t; }
    public void setTongTienThieu(long t) { this.tongTienThieu = t; }
    public void setHoNopDu(int h) { this.hoNopDu = h; }
    public void setTiLeNopDu(double t) { this.tiLeNopDu = t; }
}
