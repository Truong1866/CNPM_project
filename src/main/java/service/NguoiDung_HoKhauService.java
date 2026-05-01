package service;

import database.Database;
import models.ResidentHouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguoiDung_HoKhauService {

    public static ResidentHouse timHoKhau(String maNguoiDung) throws Exception{
        if(maNguoiDung == null) throw new Exception("Ma nguoi dung khong duoc trong");
        Connection conn = Database.connect();
        String sql = "SELECT * FROM nguoidung_hokhau WHERE MaNguoiDung = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maNguoiDung);
        ResultSet rs = pr.executeQuery();
        rs.next();

        pr.close();
        conn.close();
        return new ResidentHouse(rs.getString("MaNguoiDung"),
                                    rs.getString("MaHo"),
                                    rs.getBoolean("ChuHo"));
    }

    public static List<ResidentHouse> timNguoiDung(String maHo) throws Exception{
        if(maHo == null) throw new Exception("Ma ho khau khong duoc trong");
        Connection conn = Database.connect();
        String sql = "SELECT * FROM nguoidung_hokhau WHERE MaHo = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maHo);
        ResultSet rs = pr.executeQuery();

        List<ResidentHouse> list = new ArrayList<>();
        while (rs.next()){
            ResidentHouse nguoiDungHoKhau = new ResidentHouse(
                rs.getString("MaNguoiDung"),
                rs.getString("MaHo"),
                rs.getBoolean("ChuHo")
            );
            list.add(nguoiDungHoKhau);
        }

        pr.close();
        conn.close();
        return list;
    }

    public static void themNguoiDung_HoKhau(ResidentHouse nguoiDungHoKhau) throws Exception{
        if(nguoiDungHoKhau.getMaNguoiDung() == null) throw new Exception("Ma nguoi dung khong duoc trong");
        if(nguoiDungHoKhau.getMaHo() == null) throw new Exception("Ma ho khau khong duoc trong");

        Connection conn = Database.connect();
        String sql = "INSERT INTO nguoidung_hokhau value (?, ?, ?)";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, nguoiDungHoKhau.getMaNguoiDung());
        pr.setString(2, nguoiDungHoKhau.getMaHo());
        pr.setBoolean(3, nguoiDungHoKhau.isChuHo());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    public static void xoaNguoiDung_HoKhau(String maNguoiDung, String maHo) throws Exception{
        if(maNguoiDung == null) throw new Exception("Ma nguoi dung khong duoc trong");
        if(maHo == null) throw new Exception("Ma ho khau khong duoc trong");

        Connection conn = Database.connect();
        String sql = "DELETE FROM nguoidung_hokhau WHERE MaNguoiDung = ? AND MaHo = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maNguoiDung);
        pr.setString(2, maHo);
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    public static void suaNguoiDung_HoKhau(ResidentHouse nguoiDungHoKhau) throws Exception{
        if(nguoiDungHoKhau.getMaNguoiDung() == null) throw new Exception("Ma nguoi dung khong duoc trong");
        if(nguoiDungHoKhau.getMaHo() == null) throw new Exception("Ma ho khau khong duoc trong");

        Connection conn = Database.connect();
        String sql = "UPDATE nguoidung_hokhau SET" +
                " ChuHo = ?" +
                " WHERE MaNguoiDung = ? AND MaHo = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setBoolean(1, nguoiDungHoKhau.isChuHo());
        pr.setString(2, nguoiDungHoKhau.getMaNguoiDung());
        pr.setString(3, nguoiDungHoKhau.getMaHo());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }
}
