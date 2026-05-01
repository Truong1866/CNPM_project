package models;

import jakarta.persistence.*;

@Entity
@Table(name = "House_Recei")
public class HouseRecei {

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseReg houseReg;

    @ManyToOne
    @JoinColumn(name = "recei_id", nullable = false)
    private Receivable receivable;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "pay_date")
    private java.time.OffsetDateTime payDate;

    @Column(name = "pay_deadline", nullable = false)
    private java.time.OffsetDateTime payDeadline;

    @Column(name = "description")
    private String description;

    public HouseRecei() {}

    public HouseReg getHouseReg() {return houseReg;}
    public void setHouseReg(HouseReg houseReg) {this.houseReg = houseReg;}

    public Receivable getReceivable() {return receivable;}
    public void setReceivable(Receivable receivable) {this.receivable = receivable;}

    public boolean isStatus() {return status;}
    public void setStatus(boolean status) {this.status = status;}

    public long getQuantity() {return quantity;}
    public void setQuantity(long quantity) {this.quantity = quantity;}

    public java.time.OffsetDateTime getPayDate() {return payDate;}
    public void setPayDate(java.time.OffsetDateTime payDate) {this.payDate = payDate;}

    public java.time.OffsetDateTime getPayDeadline() {return payDeadline;}
    public void setPayDeadline(java.time.OffsetDateTime payDeadline) {this.payDeadline = payDeadline;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}
