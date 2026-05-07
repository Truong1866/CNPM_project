package service;

import models.PhiBienLai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhiBienLaiService {

    public static List<PhiBienLai> timPhiBienLai(String maPhiBienLai) throws Exception {
        Connection conn = SupabaseDatabase.connect();
        String sql = "SELECT * FROM phiBienLai WHERE MaPhiBienLai = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maPhiBienLai);
        ResultSet rs = pr.executeQuery();

        List<PhiBienLai> list = new ArrayList<>();
        while(rs.next()) {
            PhiBienLai p = new PhiBienLai(
                rs.getString("MaPhiBienLai"),
                rs.getString("MaHo"),
                rs.getString("MaKhoanThu"),
                rs.getInt("SoTienThuc"),
                rs.getDate("NgayThu").toLocalDate(),
                rs.getString("TrangThaiThu"),
                rs.getString("NguoiThu"),
                rs.getString("GhiChu")
            );
            list.add(p);
        }
        pr.close();
        conn.close();
        return list;
    }

    public static void themPhiBienLai(PhiBienLai phi) throws Exception {
        if(phi.getSoTienThuc() <= 0) throw new Exception("Số tiền phải > 0");
        if(phi.getNgayThu().isAfter(LocalDate.now())) throw new Exception("Ngày thu không được trong tương lai");

        Connection conn = SupabaseDatabase.connect();
        String sql = "INSERT INTO phiBienLai (MaPhiBienLai, MaHo, MaKhoanThu, SoTienThuc, NgayThu, TrangThaiThu, NguoiThu, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, phi.getMaPhiBienLai());
        pr.setString(2, phi.getMaHo());
        pr.setString(3, phi.getMaKhoanThu());
        pr.setInt(4, phi.getSoTienThuc());
        pr.setDate(5, java.sql.Date.valueOf(phi.getNgayThu()));
        pr.setString(6, phi.getTrangThaiThu());
        pr.setString(7, phi.getNguoiThu());
        pr.setString(8, phi.getGhiChu());
        pr.executeUpdate();
        pr.close();
        conn.close();
    }

    public static List<PhiBienLai> timPhiBienLaiTheoHo(String maHo) throws Exception {
        Connection conn = SupabaseDatabase.connect();
        String sql = "SELECT * FROM phiBienLai WHERE MaHo = ? AND TrangThaiThu = 'Đã thu'";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maHo);
        ResultSet rs = pr.executeQuery();

        List<PhiBienLai> list = new ArrayList<>();
        while(rs.next()) {
            PhiBienLai p = new PhiBienLai(
                rs.getString("MaPhiBienLai"),
                rs.getString("MaHo"),
                rs.getString("MaKhoanThu"),
                rs.getInt("SoTienThuc"),
                rs.getDate("NgayThu").toLocalDate(),
                rs.getString("TrangThaiThu"),
                rs.getString("NguoiThu"),
                rs.getString("GhiChu")
            );
            list.add(p);
        }
        pr.close();
        conn.close();
        return list;
    }

    public static List<PhiBienLai> timPhiBienLaiTheoKhoangThoiGian(LocalDate batDau, LocalDate ketThuc) throws Exception {
        Connection conn = SupabaseDatabase.connect();
        String sql = "SELECT * FROM phiBienLai WHERE NgayThu BETWEEN ? AND ? AND TrangThaiThu = 'Đã thu'";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setDate(1, java.sql.Date.valueOf(batDau));
        pr.setDate(2, java.sql.Date.valueOf(ketThuc));
        ResultSet rs = pr.executeQuery();

        List<PhiBienLai> list = new ArrayList<>();
        while(rs.next()) {
            PhiBienLai p = new PhiBienLai(
                rs.getString("MaPhiBienLai"),
                rs.getString("MaHo"),
                rs.getString("MaKhoanThu"),
                rs.getInt("SoTienThuc"),
                rs.getDate("NgayThu").toLocalDate(),
                rs.getString("TrangThaiThu"),
                rs.getString("NguoiThu"),
                rs.getString("GhiChu")
            );
            list.add(p);
        }
        pr.close();
        conn.close();
        return list;
    }

    public static void xoaPhiBienLai(String maPhiBienLai) throws Exception {
        Connection conn = SupabaseDatabase.connect();
        String sql = "UPDATE phiBienLai SET TrangThaiThu = 'Hủy' WHERE MaPhiBienLai = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maPhiBienLai);
        pr.executeUpdate();
        pr.close();
        conn.close();
    }

    public static long getTongTienThuTrongThang(int thang, int nam) throws Exception {
        Connection conn = SupabaseDatabase.connect();
        String sql = "SELECT COALESCE(SUM(SoTienThuc), 0) AS total FROM phiBienLai WHERE MONTH(NgayThu) = ? AND YEAR(NgayThu) = ? AND TrangThaiThu = 'Đã thu'";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setInt(1, thang);
        pr.setInt(2, nam);
        ResultSet rs = pr.executeQuery();
        long total = 0;
        if(rs.next()) {
            total = rs.getLong("total");
        }
        pr.close();
        conn.close();
        return total;
    }
}

