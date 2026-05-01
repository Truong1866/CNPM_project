package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Apartment")
public class Apartment {
    @Id
    @Column(name = "apart_id", nullable = false, unique = true, length = 20)
    private String apartId;

    @Column(name = "house_id", nullable = false, length = 20)
    private String houseId;

    @Column(name = "room_number", nullable = false, length = 20)
    private String roomNumber;

    @Column(name = "area", nullable = false)
    private double area;

    @Column(name ="description")
    private String description;

    @Column(name = "delete_at")
    private java.sql.Timestamp deleteAt;

    public Apartment() {}
    public String getApartId() {return apartId;}
    public void setApartId(String apartId) {this.apartId = apartId;}

    public String getHouseId() {return houseId;}
    public void setHouseId(String houseId) {this.houseId = houseId;}

    public String getRoomNumber() {return roomNumber;}
    public void setRoomNumber(String roomNumber) {this.roomNumber = roomNumber;}

    public double getArea() {return area;}
    public void setArea(double area) {this.area = area;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public java.sql.Timestamp getDeleteAt() {return deleteAt;}
    public void setDeleteAt(java.sql.Timestamp deleteAt) {this.deleteAt = deleteAt;}
}
