package service;

import database.Database;
import models.Apartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CanHoServices {
    /// can cap nhat them kha nang thong bao khong co can ho ton tai
    public static List<Apartment> timCanHo(String ma_so) throws Exception{
        Connection conn = Database.connect();
        String sql = "SELECT * FROM canho WHERE MaCanHo = ? OR SoPhong = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, ma_so);
        pr.setString(2, ma_so);
        ResultSet rs = pr.executeQuery();

        List<Apartment> list = new ArrayList<>();
        while (rs.next()){
            Apartment apartment = new Apartment(
                rs.getString("MaCanHo"),
                rs.getString("SoPhong"),
                rs.getInt("DienTich"),
                rs.getString("MoTa")
            );
            list.add(apartment);
        }
        pr.close();
        conn.close();

        return list;
    }

    /// can cap nhat kha nang tao ma tu dong theo so phong
    /// can cap nhat them kha nang thong bao loi neu can ho da ton tai
    public static void themCanHo(Apartment apartment) throws Exception{
        if(apartment.getMaCanHo() == null) throw new Exception("Ma can ho khong duoc trong");
        if(apartment.getSoPhong() == null) throw new Exception("So phong khong duoc trong");
        if(apartment.getDienTich() < 0) throw new Exception("Dien tich khong duoc am");

        Connection conn = Database.connect();
        String sql = "INSERT INTO canho value (?, ?, ?, ?)";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, apartment.getMaCanHo());
        pr.setString(2, apartment.getSoPhong());
        pr.setInt(3, apartment.getDienTich());
        pr.setString(4, apartment.getMoTa());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can cap nhat them kha nang thong bao neu can ho khong ton tai
    /// can dam bao viec xoa can ho phai xoa ca ho khau va nhan khau o can ho truoc
    public static void xoaCanHo(String ma_so) throws Exception{
        Connection conn = Database.connect();
        String sql = "DELETE FROM canho WHERE MaCanHo = ? OR SoPhong = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, ma_so);
        pr.setString(2, ma_so);
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can cap nhat them kha nang thong bao can ho khong ton tai
    public static void suaCanHo(Apartment apartment) throws Exception{
        if(apartment.getMaCanHo() == null) throw new Exception("Ma can ho khong duoc trong");
        if(apartment.getSoPhong() == null) throw new Exception("So phong khong duoc trong");
        if(apartment.getDienTich() < 0) throw new Exception("Dien tich khong duoc am");

        Connection conn = Database.connect();
        String sql = "UPDATE canho SET" +
                " DienTich = ?" +
                " ,MoTa = ?" +
                " WHERE MaCanHo = ? AND SoPhong = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setInt(1, apartment.getDienTich());
        pr.setString(2, apartment.getMoTa());
        pr.setString(3, apartment.getMaCanHo());
        pr.setString(4, apartment.getSoPhong());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }
}
