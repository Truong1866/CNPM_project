package service;

import database.Database;
import models.Receivable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KhoanThuServices {
    /// can cap nhat them kha nang thong bao loi khoan thu khong ton tai
    public static List<Receivable> timKhoanThu(String ma_ten) throws Exception{
        Connection conn = Database.connect();
        String sql = "SELECT * FROM khoanthu WHERE MaKhoanThu = ? OR TenKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, ma_ten);
        pr.setString(2, ma_ten);
        ResultSet rs = pr.executeQuery();

        List<Receivable> list = new ArrayList<>();
        while (rs.next()){
            Receivable receivable = new Receivable(
                rs.getString("MaKhoanThu"),
                rs.getString("TenKhoanThu"),
                rs.getBoolean("BatBuoc"),
                rs.getBoolean("CoDinh"),
                rs.getInt("DonGia"),
                rs.getString("MoTa")
            );
            list.add(receivable);
        }
        pr.close();
        conn.close();

        return list;
    }

    /// can cap nhat them kha nang thong bao loi khoan thu khong ton tai
    /// can cap nhat them kha nang thong bao loi khoan thu bat duoc co ho khau lien ket voi no
    public static void xoaKhoanThu(String maKhoanThu) throws Exception{
        Connection conn = Database.connect();
        String sql = "DELETE FROM khoanthu WHERE MaKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maKhoanThu);
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can cap nhat them kha nang thong bao loi khoan thu da ton tai
    /// can cap nhat them kha nang thong bao loi ten khoan thu bi trung
    public static void themKhoanThu(Receivable receivable) throws Exception{
        if(receivable.getMaKhoanThu() == null) throw new Exception("Ma khoan thu khong duoc trong");
        if(receivable.getTenKhoanThu() == null) throw new Exception("Ten khoan thu khong duoc trong");
        if(receivable.getDonGia() < 0) throw new Exception("Don gia khong duoc am");

        Connection conn = Database.connect();
        String sql = "INSERT INTO khoanthu value (?, ?, ?, ?, ?, ?)";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, receivable.getMaKhoanThu());
        pr.setString(2, receivable.getTenKhoanThu());
        pr.setBoolean(3, receivable.isBatBuoc());
        pr.setBoolean(4, receivable.isCoDinh());
        pr.setInt(5, receivable.getDonGia());
        pr.setString(6, receivable.getMoTa());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can cap nhat them kha nang thong bao loi khoan thu khong ton tai
    /// can cap nhat them kha nang thong bao loi ten khoan thu thay doi bi trung
    public static void suaKhoanThu(Receivable receivable) throws Exception{
        if(receivable.getMaKhoanThu() == null) throw new Exception("Ma khoan thu khong duoc trong");
        if(receivable.getTenKhoanThu() == null) throw new Exception("Ten khoan thu khong duoc trong");
        if(receivable.getDonGia() < 0) throw new Exception("Don gia khong duoc am");

        Connection conn = Database.connect();
        String sql = "UPDATE khoanthu SET" +
                " TenKhoanThu = ?" +
                " ,BatBuoc = ?" +
                " ,CoDinh = ?" +
                " ,DonGia = ?" +
                " ,MoTa = ?" +
                " WHERE MaKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, receivable.getTenKhoanThu());
        pr.setBoolean(2, receivable.isBatBuoc());
        pr.setBoolean(3, receivable.isCoDinh());
        pr.setInt(4, receivable.getDonGia());
        pr.setString(5, receivable.getMoTa());
        pr.setString(6, receivable.getMaKhoanThu());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }
}
