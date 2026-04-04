package application;

import javafx.application.Application;
import javafx.stage.Stage;
import moduls.CanHo;
import moduls.HoKhau_KhoanThu;
import moduls.NguoiDung;
import moduls.NguoiDung_HoKhau;
import service.*;

import java.sql.Connection;
import java.util.List;


public class main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            try {
                List<NguoiDung_HoKhau> list = NguoiDung_HoKhauService.timNguoiDung("101B");
                for(NguoiDung_HoKhau nguoiDungHoKhau: list){
                    System.out.println(nguoiDungHoKhau.getMaNguoiDung() + " "
                                      +nguoiDungHoKhau.getMaHo() + " "
                                      +nguoiDungHoKhau.isChuHo());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
