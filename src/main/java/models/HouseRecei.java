package models;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "house_recei")
public class HouseRecei {

    @ManyToOne
    @Id
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
    private Instant payDate;

    @Column(name = "pay_deadline", nullable = false)
    private Instant payDeadline;

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

    public Instant getPayDate() {return payDate;}
    public void setPayDate(Instant payDate) {this.payDate = payDate;}

    public Instant getPayDeadline() {return payDeadline;}
    public void setPayDeadline(Instant payDeadline) {this.payDeadline = payDeadline;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseRecei that = (HouseRecei) o;

        return Objects.equals(houseReg != null ? houseReg.getHouseId() : null,
                that.houseReg != null ? that.houseReg.getHouseId() : null) &&
                Objects.equals(receivable != null ? receivable.getReceiId() : null,
                        that.receivable != null ? that.receivable.getReceiId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseReg != null ? houseReg.getHouseId() : null,
                receivable != null ? receivable.getReceiId() : null);
    }
}
