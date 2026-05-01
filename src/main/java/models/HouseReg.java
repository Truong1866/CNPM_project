package models;

import jakarta.persistence.*;

@Entity
@Table(name = "")
public class HouseReg {
    private String maHo;
    private String maCanHo;

    public String getHo() {
        return maHo;
    }

    public String getMaCanHo() {
        return maCanHo;
    }

    public HouseReg(){}

    public HouseReg(String maHo, String maCanHo){
        this.maHo = maHo;
        this.maCanHo = maCanHo;
    }
}
