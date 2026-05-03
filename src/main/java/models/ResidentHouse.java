package models;

import jakarta.persistence.*;

@Entity
@Table(name = "resident_house")
public class ResidentHouse {
    @ManyToOne
    @Id
    @JoinColumn(name = "resident_id", unique = true, nullable = false)
    private Resident resident;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseReg houseReg;

    @Column(name = "isMaster", nullable = false)
    private boolean isMaster;

    public ResidentHouse() {}

    public Resident getResident() {return resident;}
    public void setResident(Resident resident) {this.resident = resident;}

    public HouseReg getHouseReg() {return houseReg;}
    public void setHouseReg(HouseReg houseReg) {this.houseReg = houseReg;}

    public boolean isMaster() {return isMaster;}
    public void setIsMaster(boolean isMaster) {this.isMaster = isMaster;}
}
