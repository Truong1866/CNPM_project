package service;

import models.KhoanThu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KhoanThuServices {
    /// can cap nhat them kha nang thong bao loi khoan thu khong ton tai
    public static List<KhoanThu> timKhoanThu(String ma_ten) throws Exception{
        Connection conn = MySQL.connect();
        String sql = "SELECT * FROM khoanthu WHERE MaKhoanThu = ? OR TenKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, ma_ten);
        pr.setString(2, ma_ten);
        ResultSet rs = pr.executeQuery();

        List<KhoanThu> list = new ArrayList<>();
        while (rs.next()){
            KhoanThu khoanThu = new KhoanThu(
                rs.getString("MaKhoanThu"),
                rs.getString("TenKhoanThu"),
                rs.getBoolean("BatBuoc"),
                rs.getBoolean("CoDinh"),
                rs.getInt("DonGia"),
                rs.getString("MoTa")
            );
            list.add(khoanThu);
        }
        pr.close();
        conn.close();

        return list;
    }

    /// can cap nhat them kha nang thong bao loi khoan thu khong ton tai
    /// can cap nhat them kha nang thong bao loi khoan thu bat duoc co ho khau lien ket voi no
    public static void xoaKhoanThu(String maKhoanThu) throws Exception{
        Connection conn = MySQL.connect();
        String sql = "DELETE FROM khoanthu WHERE MaKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maKhoanThu);
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can cap nhat them kha nang thong bao loi khoan thu da ton tai
    /// can cap nhat them kha nang thong bao loi ten khoan thu bi trung
    public static void themKhoanThu(KhoanThu khoanThu) throws Exception{
        if(khoanThu.getMaKhoanThu() == null) throw new Exception("Ma khoan thu khong duoc trong");
        if(khoanThu.getTenKhoanThu() == null) throw new Exception("Ten khoan thu khong duoc trong");
        if(khoanThu.getDonGia() < 0) throw new Exception("Don gia khong duoc am");

        Connection conn = MySQL.connect();
        String sql = "INSERT INTO khoanthu value (?, ?, ?, ?, ?, ?)";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, khoanThu.getMaKhoanThu());
        pr.setString(2, khoanThu.getTenKhoanThu());
        pr.setBoolean(3, khoanThu.isBatBuoc());
        pr.setBoolean(4, khoanThu.isCoDinh());
        pr.setInt(5, khoanThu.getDonGia());
        pr.setString(6, khoanThu.getMoTa());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can cap nhat them kha nang thong bao loi khoan thu khong ton tai
    /// can cap nhat them kha nang thong bao loi ten khoan thu thay doi bi trung
    public static void suaKhoanThu(KhoanThu khoanThu) throws Exception{
        if(khoanThu.getMaKhoanThu() == null) throw new Exception("Ma khoan thu khong duoc trong");
        if(khoanThu.getTenKhoanThu() == null) throw new Exception("Ten khoan thu khong duoc trong");
        if(khoanThu.getDonGia() < 0) throw new Exception("Don gia khong duoc am");

        Connection conn = MySQL.connect();
        String sql = "UPDATE khoanthu SET" +
                " TenKhoanThu = ?" +
                " ,BatBuoc = ?" +
                " ,CoDinh = ?" +
                " ,DonGia = ?" +
                " ,MoTa = ?" +
                " WHERE MaKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, khoanThu.getTenKhoanThu());
        pr.setBoolean(2, khoanThu.isBatBuoc());
        pr.setBoolean(3, khoanThu.isCoDinh());
        pr.setInt(4, khoanThu.getDonGia());
        pr.setString(5, khoanThu.getMoTa());
        pr.setString(6, khoanThu.getMaKhoanThu());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }
}
