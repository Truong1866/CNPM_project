package service;

import database.Database;
import models.HouseReg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HoKhauServices {
    /// can cap nhap kha nag thong bao ho khau khong ton tai
    public static List<HouseReg> timHoKhau(String maHo) throws Exception{
        Connection conn = Database.connect();
        String sql = "SELECT * FROM hokhau WHERE maHo = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maHo);
        ResultSet rs = pr.executeQuery();

        List<HouseReg> list = new ArrayList<>();
        while(rs.next()){
            HouseReg housereg = new HouseReg(
                rs.getString("MaHo"),
                rs.getString("MaCanHo")
            );
            list.add(housereg);
        }
        pr.close();
        conn.close();

        return list;
    }

    /// can cap nhat kha nang thong bao ho khau khong ton tai
    /// can cap nhat kha nang thong bao can xoa tat ca nguoi dan trong ho khau
    public static void xoaHoKhau(String maHo) throws Exception{
        Connection conn = Database.connect();
        String sql = "DELETE FROM hokhau WHERE maHo = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maHo);
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can cap nhat kha nang thong bao loi ho khau da ton tai
    /// can cap nhat kha nang thong bao loi can ho khong trong (da co ho khau)
    public static void themHoKhau(HouseReg housereg) throws Exception{
        if(housereg.getHo() == null) throw new Exception("Ma ho khau khong duoc trong");
        if(housereg.getMaCanHo() == null) throw new Exception("Ma can ho khong duoc trong");

        Connection conn = Database.connect();
        String sql = "INSERT INTO hokhau value (?, ?)";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, housereg.getHo());
        pr.setString(2, housereg.getMaCanHo());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can cap nhat kha nang thong bao loi ho khau khong ton tai
    /// can cap nhat kha nang thong bao can ho khong trong (da co ho khau)
    public static void suaHoKhau(HouseReg housereg) throws Exception{
        if(housereg.getHo() == null) throw new Exception("Ma ho khau khong duoc trong");
        if(housereg.getMaCanHo() == null) throw new Exception("Ma can ho khong duoc trong");

        Connection conn = Database.connect();
        String sql = "UPDATE hokhau SET" +
                " MaCanHo = ?" +
                " WHERE MaHo = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, housereg.getMaCanHo());
        pr.setString(2, housereg.getHo());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }
}
