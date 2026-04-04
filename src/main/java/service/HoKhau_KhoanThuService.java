package service;

import moduls.HoKhau_KhoanThu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HoKhau_KhoanThuService {
    /// <summary>
    /// Tim tat ca khoan thu cua ho khau dua tren ma ho khau
    /// </summary>
    /// <param name="maHoKhau">ma ho khau</param>
    /// <return>danh sach cac khoan</return>

    public static List<HoKhau_KhoanThu> timKhoanThu(String maHo) throws Exception{
        if (maHo == null) throw new Exception("Ma ho khau khong duoc trong");
        Connection conn = MySQL.connect();
        String sql = "SELECT * FROM hokhau_khoanthu WHERE MaHo = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maHo);
        ResultSet rs = pr.executeQuery();

        List<HoKhau_KhoanThu> list = new ArrayList<>();
        while(rs.next()){
            HoKhau_KhoanThu hoKhauKhoanThu = new HoKhau_KhoanThu(
                rs.getString("MaHo"),
                rs.getString("MaKhoanThu"),
                rs.getBoolean("TrangThai"),
                rs.getInt("SoLuong"),
                rs.getString("NgayNop"),
                rs.getString("HanNop"),
                rs.getString("MoTa")
            );
            list.add(hoKhauKhoanThu);
        }

        pr.close();
        conn.close();
        return list;
    }

    /// <summary>
    /// Tim tat ca ho khau cua khoan thu dua tren ma khoan thu
    /// </summary>
    /// <param name="maKhoanThu">ma khoan thu</param>
    /// <return>danh sach cac ho</return>

    public static List<HoKhau_KhoanThu> timHoKhau(String maKhoanThu) throws Exception{
        if(maKhoanThu == null) throw new Exception("Ma khoan thu khong duoc trong");
        Connection conn = MySQL.connect();
        String sql = "SELECT * FROM hokhau_khoanthu WHERE MaKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maKhoanThu);
        ResultSet rs = pr.executeQuery();

        List<HoKhau_KhoanThu> list = new ArrayList<>();
        while(rs.next()){
            HoKhau_KhoanThu hoKhauKhoanThu = new HoKhau_KhoanThu(
                    rs.getString("MaHo"),
                    rs.getString("MaKhoanThu"),
                    rs.getBoolean("TrangThai"),
                    rs.getInt("SoLuong"),
                    rs.getString("NgayNop"),
                    rs.getString("HanNop"),
                    rs.getString("MoTa")
            );
            list.add(hoKhauKhoanThu);
        }

        pr.close();
        conn.close();
        return list;
    }

    /// <summary>
    /// them quan he khoan thu va ho khau vao database
    /// </summary>
    /// <param name="hoKhauKhoanThu">doi tuong quan he khoan thu va ho khau da co</param>
    /// <return></return>

    public static void themHoKhau_KhoanThu(HoKhau_KhoanThu hoKhauKhoanThu) throws Exception{
        if(hoKhauKhoanThu.getMaHo() == null) throw new Exception("Ma ho khong duoc trong");
        if(hoKhauKhoanThu.getMaKhoanThu() == null) throw new Exception("Ma khoan thu khong duoc trong");
        if(hoKhauKhoanThu.getSoLuong() <= 0) throw new Exception("So luong phai duong");

        Connection conn = MySQL.connect();
        String sql = "INSERT INTO hokhau_canho value (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, hoKhauKhoanThu.getMaHo());
        pr.setString(2, hoKhauKhoanThu.getMaKhoanThu());
        pr.setBoolean(3, hoKhauKhoanThu.isTrangThai());
        pr.setInt(4, hoKhauKhoanThu.getSoLuong());
        pr.setString(5, hoKhauKhoanThu.getNgayNop());
        pr.setString(6, hoKhauKhoanThu.getHanNop());
        pr.setString(7, hoKhauKhoanThu.getMoTa());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// <summary>
    /// xoa quan he khoan thu va ho khau khoi database
    /// </summary>
    /// <param name="maHo">ma ho khau</param>
    /// <param name="maKhoanThu">ma khoan thu </param>
    /// <return></return>
    public static void xoaHoKhau_KhoanThu(String maHo, String maKhoanTHu) throws Exception{
        if(maHo == null) throw new Exception("Ma ho khong duoc trong");
        if(maKhoanTHu == null) throw new Exception("Ma khoan thu khong duoc trong");
        Connection conn = MySQL.connect();
        String sql = "DELETE FROM hokhau_khoanthu WHERE MaHo = ? AND MaKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setString(1, maHo);
        pr.setString(2, maKhoanTHu);
        pr.executeUpdate();

        pr.close();
        conn.close();
    }

    /// <summary>
    /// xoa quan he khoan thu va ho khau khoi database
    /// </summary>
    /// <param name="maHo">ma ho khau</param>
    /// <param name="maKhoanThu">ma khoan thu </param>
    /// <return></return>
    public static void suaHoKhau_KhoanThu(HoKhau_KhoanThu hoKhauKhoanThu) throws Exception{
        if(hoKhauKhoanThu.getMaHo() == null) throw new Exception("Ma ho khong duoc trong");
        if(hoKhauKhoanThu.getMaKhoanThu() == null) throw new Exception("Ma khoan thu khong duoc trong");
        if(hoKhauKhoanThu.getSoLuong() <= 0) throw new Exception("So luong phai duong");

        Connection conn = MySQL.connect();
        String sql = "UPDATE hokhau_khoanthu SET" +
                " TrangThai = ?" +
                " ,SoLuong = ?" +
                " ,NgayNop = ?" +
                " ,HanNop = ?" +
                " ,MoTa = ?" +
                " WHERE MaHo = ? AND MaKhoanThu = ?";
        PreparedStatement pr = conn.prepareStatement(sql);
        pr.setBoolean(1, hoKhauKhoanThu.isTrangThai());
        pr.setInt(2, hoKhauKhoanThu.getSoLuong());
        pr.setString(3, hoKhauKhoanThu.getNgayNop());
        pr.setString(4, hoKhauKhoanThu.getHanNop());
        pr.setString(5, hoKhauKhoanThu.getMoTa());
        pr.setString(6, hoKhauKhoanThu.getMaHo());
        pr.setString(7, hoKhauKhoanThu.getMaKhoanThu());
        pr.executeUpdate();

        pr.close();
        conn.close();
    }
}
