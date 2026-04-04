package service;

import moduls.NguoiDung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungService {
    /// can cap nhat them kha nang thong bao khong co nguoi dung ton tai
    public static List<NguoiDung> timNguoiDung(String ma_ten_CCCD) throws Exception{
        Connection conn = MySQL.connect();
        String sql = "SELECT * FROM nguoidung WHERE TenNguoiDung = ? OR MaNguoiDung = ? OR CCCD = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, ma_ten_CCCD);
        pr.setString(2, ma_ten_CCCD);
        pr.setString(3, ma_ten_CCCD);
        ResultSet rs = pr.executeQuery();

        List<NguoiDung> list = new ArrayList<>();
        while (rs.next()){
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
        pr.close();
        conn.close();

        return list;
    }


    ///  can cap nhat them kha nang tao ma tu dong
    ///  can cap nhat them kha nang bao cao loi neu ma nguoi dung da ton tai
    public static void themNguoiDung(NguoiDung nguoiDung) throws Exception{
        if(nguoiDung.getMaNguoiDung() == null) throw new Exception("Ma nguoi dung khong duoc trong");
        if(nguoiDung.getTenNguoiDung() == null) throw new Exception("Ten nguoi dung khong duoc trong");
        if(nguoiDung.getMatKhau() == null) throw new Exception("Mat khau khong duoc trong");
        if(nguoiDung.getCCCD() == null) throw new  Exception("CCCD khong duoc trong");

        Connection conn = MySQL.connect();
        String sql = "INSERT INTO nguoidung VALUE (?, ?, ?, ?, ?, ?)";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, nguoiDung.getMaNguoiDung());
        pr.setString(2, nguoiDung.getTenNguoiDung());
        pr.setString(3, nguoiDung.getMatKhau());
        pr.setString(4, nguoiDung.getVaiTro());
        pr.setString(5, nguoiDung.getDienThoai());
        pr.setString(6, nguoiDung.getCCCD());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can thong bao kha nang xoa nguoi dung khong ton tai
    public static void xoaNguoiDung(String maNguoiDung) throws Exception{
        Connection conn = MySQL.connect();
        String sql = "DELETE FROM nguoidung WHERE MaNguoiDung = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maNguoiDung);
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// can thong bao kha nang sua nguoi khong ton tai
    public static void suaNguoiDung(NguoiDung nguoiDung) throws Exception{
        if(nguoiDung.getMaNguoiDung() == null) throw new Exception("Ma nguoi dung khong duoc trong");
        if(nguoiDung.getTenNguoiDung() == null) throw new Exception("Ten nguoi dung khong duoc trong");
        if(nguoiDung.getMatKhau() == null) throw new Exception("Mat khau khong duoc trong");
        if(nguoiDung.getCCCD() == null) throw new Exception("CCCD khong duoc trong");

        Connection conn = MySQL.connect();
        String sql = "UPDATE nguoidung SET" +
                " TenNguoiDung = ?" +
                " ,MatKhau = ?" +
                " ,VaiTro = ?" +
                " ,DienThoai = ?" +
                " ,CCCD = ?" +
                " WHERE MaNguoiDung = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, nguoiDung.getTenNguoiDung());
        pr.setString(2, nguoiDung.getMatKhau());
        pr.setString(3, nguoiDung.getVaiTro());
        pr.setString(4, nguoiDung.getDienThoai());
        pr.setString(5, nguoiDung.getCCCD());
        pr.setString(6, nguoiDung.getMaNguoiDung());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }
}
