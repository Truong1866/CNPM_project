package service;

import models.HoKhau;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HoKhauServices {

    public static List<HoKhau> timHoKhau(String maHo) throws Exception {
        String sql = "SELECT * FROM hokhau WHERE maHo = ?";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setString(1, maHo);
            try (ResultSet rs = pr.executeQuery()) {
                List<HoKhau> list = new ArrayList<>();
                while (rs.next()) {
                    HoKhau hoKhau = new HoKhau(
                            rs.getString("MaHo"),
                            rs.getString("MaCanHo")
                    );
                    list.add(hoKhau);
                }
                return list;
            }
        }
    }

    public static void xoaHoKhau(String maHo) throws Exception {
        String sql = "DELETE FROM hokhau WHERE maHo = ?";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setString(1, maHo);
            pr.executeUpdate();
        }
    }

    public static void themHoKhau(HoKhau hoKhau) throws Exception {
        if (hoKhau.getHo() == null) throw new Exception("Ma ho khau khong duoc trong");
        if (hoKhau.getMaCanHo() == null) throw new Exception("Ma can ho khong duoc trong");

        String sql = "INSERT INTO hokhau value (?, ?)";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setString(1, hoKhau.getHo());
            pr.setString(2, hoKhau.getMaCanHo());
            pr.executeUpdate();
        }
    }

    public static void suaHoKhau(HoKhau hoKhau) throws Exception {
        if (hoKhau.getHo() == null) throw new Exception("Ma ho khau khong duoc trong");
        if (hoKhau.getMaCanHo() == null) throw new Exception("Ma can ho khong duoc trong");

        String sql = "UPDATE hokhau SET MaCanHo = ? WHERE MaHo = ?";
        try (Connection conn = MySQL.connect();
             PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setString(1, hoKhau.getMaCanHo());
            pr.setString(2, hoKhau.getHo());
            pr.executeUpdate();
        }
    }
}
