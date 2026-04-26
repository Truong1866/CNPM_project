package service;

import models.BaoCaoThongKe;
import models.HoKhau_KhoanThu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaoCaoThongKeService {

    public static List<BaoCaoThongKe> layThongKeTheoKhoan() throws Exception {
        String sql = "SELECT * FROM v_tongKeThongKe";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql);
             ResultSet rs = pr.executeQuery()) {

            List<BaoCaoThongKe> list = new ArrayList<>();
            while (rs.next()) {
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
            return list;
        }
    }

    public static long layTongDoanh() throws Exception {
        String sql = "SELECT COALESCE(SUM(SoTienThuc), 0) AS total FROM phiBienLai WHERE TrangThaiThu = 'Đã thu'";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql);
             ResultSet rs = pr.executeQuery()) {

            if (rs.next()) {
                return rs.getLong("total");
            }
            return 0;
        }
    }

    public static double layTiLeNopThu() throws Exception {
        String sql = "SELECT (COALESCE(SUM(hk.SoTienThuc), 0) * 100.0 / COALESCE(SUM(kt.DonGia * c.DienTich), 1)) AS tiLe " +
                     "FROM hokhau_khoanthu hk " +
                     "JOIN khoanthu kt ON hk.MaKhoanThu = kt.MaKhoanThu " +
                     "JOIN hokhau h ON hk.MaHo = h.MaHo " +
                     "JOIN canho c ON h.MaCanHo = c.MaCanHo";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql);
             ResultSet rs = pr.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("tiLe");
            }
            return 0;
        }
    }

    public static List<HoKhau_KhoanThu> layDSHoQuaHan(int soNgayQuaHan) throws Exception {
        String sql = "SELECT * FROM hokhau_khoanthu WHERE HanNop < DATE_SUB(NOW(), INTERVAL ? DAY) AND TrangThaiChiTiet != 'Nộp đủ'";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setInt(1, soNgayQuaHan);
            try (ResultSet rs = pr.executeQuery()) {
                List<HoKhau_KhoanThu> list = new ArrayList<>();
                while (rs.next()) {
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
                return list;
            }
        }
    }
}
