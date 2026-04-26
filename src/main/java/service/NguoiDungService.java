package service;

import models.NguoiDung;
import validation.ValidationUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungService {

    public static List<NguoiDung> timNguoiDung(String ma_ten_CCCD) throws Exception {
        String sql = "SELECT * FROM nguoidung WHERE TenNguoiDung = ? OR MaNguoiDung = ? OR CCCD = ?";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setString(1, ma_ten_CCCD);
            pr.setString(2, ma_ten_CCCD);
            pr.setString(3, ma_ten_CCCD);
            try (ResultSet rs = pr.executeQuery()) {
                List<NguoiDung> list = new ArrayList<>();
                while (rs.next()) {
                    NguoiDung nguoiDung = new NguoiDung(
                            rs.getString("MaNguoiDung"),
                            rs.getString("TenNguoiDung"),
                            rs.getString("MatKhau"),
                            rs.getString("VaiTro"),
                            rs.getString("DienThoai"),
                            rs.getString("CCCD")
                    );
                    list.add(nguoiDung);
                }
                return list;
            }
        }
    }

    public static void themNguoiDung(NguoiDung nguoiDung) throws Exception {
        if (nguoiDung.getMaNguoiDung() == null) throw new Exception("Ma nguoi dung khong duoc trong");
        if (nguoiDung.getTenNguoiDung() == null) throw new Exception("Ten nguoi dung khong duoc trong");
        if (nguoiDung.getMatKhau() == null) throw new Exception("Mat khau khong duoc trong");
        if (nguoiDung.getCCCD() == null || !ValidationUtil.isValidCCCD(nguoiDung.getCCCD())) throw new Exception("CCCD khong hop le");
        if (nguoiDung.getDienThoai() != null && !ValidationUtil.isValidPhone(nguoiDung.getDienThoai())) throw new Exception("Dien thoai khong hop le");

        String sql = "INSERT INTO nguoidung VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setString(1, nguoiDung.getMaNguoiDung());
            pr.setString(2, nguoiDung.getTenNguoiDung());
            pr.setString(3, nguoiDung.getMatKhau());
            pr.setString(4, nguoiDung.getVaiTro());
            pr.setString(5, nguoiDung.getDienThoai());
            pr.setString(6, nguoiDung.getCCCD());
            pr.executeUpdate();
        }
    }

    public static void xoaNguoiDung(String maNguoiDung) throws Exception {
        String sql = "DELETE FROM nguoidung WHERE MaNguoiDung = ?";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setString(1, maNguoiDung);
            pr.executeUpdate();
        }
    }

    public static void suaNguoiDung(NguoiDung nguoiDung) throws Exception {
        if (nguoiDung.getMaNguoiDung() == null) throw new Exception("Ma nguoi dung khong duoc trong");
        if (nguoiDung.getTenNguoiDung() == null) throw new Exception("Ten nguoi dung khong duoc trong");
        if (nguoiDung.getMatKhau() == null) throw new Exception("Mat khau khong duoc trong");
        if (nguoiDung.getCCCD() == null || !ValidationUtil.isValidCCCD(nguoiDung.getCCCD())) throw new Exception("CCCD khong hop le");
        if (nguoiDung.getDienThoai() != null && !ValidationUtil.isValidPhone(nguoiDung.getDienThoai())) throw new Exception("Dien thoai khong hop le");

        String sql = "UPDATE nguoidung SET TenNguoiDung = ?, MatKhau = ?, VaiTro = ?, DienThoai = ?, CCCD = ? WHERE MaNguoiDung = ?";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setString(1, nguoiDung.getTenNguoiDung());
            pr.setString(2, nguoiDung.getMatKhau());
            pr.setString(3, nguoiDung.getVaiTro());
            pr.setString(4, nguoiDung.getDienThoai());
            pr.setString(5, nguoiDung.getCCCD());
            pr.setString(6, nguoiDung.getMaNguoiDung());
            pr.executeUpdate();
        }
    }
}
