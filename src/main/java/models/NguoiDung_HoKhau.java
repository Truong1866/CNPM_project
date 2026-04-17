package models;

public class NguoiDung_HoKhau {
    private String maNguoiDung;
    private String maHo;
    private boolean chuHo;

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public String getMaHo(){
        return maHo;
    }

    public boolean isChuHo() {
        return chuHo;
    }

    public NguoiDung_HoKhau(){}

    public NguoiDung_HoKhau(String maNguoiDung, String maHo, boolean chuHo){
        this.maNguoiDung = maNguoiDung;
        this.maHo = maHo;
        this.chuHo = chuHo;
    }
}
