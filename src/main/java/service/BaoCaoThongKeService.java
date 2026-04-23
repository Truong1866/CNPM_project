package service;
import models.BaoCaoThongKe;
import models.HoKhau_KhoanThu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class BaoCaoThongKeService {
    public static List<BaoCaoThongKe> layThongKeTheoKhoan() throws Exception {
        Connection conn = MySQL.connect();
        String sql = "SELECT * FROM v_tongKeThongKe";
        PreparedStatement pr = conn.prepareStatement(sql);
        ResultSet rs = pr.executeQuery();
        List<BaoCaoThongKe> list = new ArrayList<>();
        while(rs.next()) {
            BaoCaoThongKe b = new BaoCaoThongKe(
                rs.getString("MaKhoanThu"),
                rs.getString("TenKhoanThu"),
                rs.getInt("TongHoCoKhoan"),
                rs.getLong("TongTienThu"),
                rs.getLong("TongTienThieu"),
                rs.getInt("HoNopDu"),
                rs.getDouble("TiLeNopDu")
            );
            list.add(b);
        }
        pr.close();
        conn.close();
        return list;
    }
    public static long layTongDoanh() throws Exception {
        Connection conn = MySQL.connect();
        String sql = "SELECT COALESCE(SUM(SoTienThuc), 0) AS total FROM phiBienLai WHERE TrangThaiThu = 'Đã thu'";
        PreparedStatement pr = conn.prepareStatement(sql);
        ResultSet rs = pr.executeQuery();
        long total = 0;
        if(rs.next()) total = rs.getLong("total");
        pr.close();
        conn.close();
        return total;
    }
    public static double layTiLeNopThu() throws Exception {
        Connection conn = MySQL.connect();
        String sql = "SELECT (COALESCE(SUM(hk.SoTienThuc), 0) * 100.0 / COALESCE(SUM(kt.DonGia * c.DienTich), 1)) AS tiLe FROM hokhau_khoanthu hk JOIN khoanthu kt ON hk.MaKhoanThu = kt.MaKhoanThu JOIN hokhau h ON hk.MaHo = h.MaHo JOIN canho c ON h.MaCanHo = c.MaCanHo";
        PreparedStatement pr = conn.prepareStatement(sql);
        ResultSet rs = pr.executeQuery();
        double tiLe = 0;
        if(rs.next()) tiLe = rs.getDouble("tiLe");
        pr.close();
        conn.close();
        return tiLe;
    }
    public static List<HoKhau_KhoanThu> layDSHoQuaHan(int soNgayQuaHan) throws Exception {
        Connection conn = MySQL.connect();
        String sql = "SELECT * FROM hokhau_khoanthu WHERE HanNop < DATE_SUB(NOW(), INTERVAL ? DAY) AND TrangThaiChiTiet != 'Nộp đủ'";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setInt(1, soNgayQuaHan);
        ResultSet rs = pr.executeQuery();
        List<HoKhau_KhoanThu> list = new ArrayList<>();
        while(rs.next()) {
            HoKhau_KhoanThu h = new HoKhau_KhoanThu(
                rs.getString("MaHo"),
                rs.getString("MaKhoanThu"),
                rs.getBoolean("TrangThai"),
                rs.getInt("SoLuong"),
                rs.getString("NgayNop"),
                rs.getString("HanNop"),
                rs.getString("MoTa")
            );
            list.add(h);
        }
        pr.close();
        conn.close();
        return list;
    }
}
