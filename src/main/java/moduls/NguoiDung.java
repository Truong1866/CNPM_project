package moduls;

public class NguoiDung {
    private String tenNguoiDung;
    private String maNguoiDung;
    private String matKhau;
    private String vaiTro;
    private String dienThoai;
    private String CCCD;

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NguoiDung(){}

    public NguoiDung(String maNguoiDung, String tenNguoiDung, String matKhau, String vaiTro, String dienThoai, String CCCD){
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        if(matKhau == null) matKhau = "12345678";
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.dienThoai = dienThoai;
        this.CCCD = CCCD;
    }
}
