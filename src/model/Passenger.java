package model;

import enumeration.TripStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahsa Alikhani m-58
 */
@Data
@Entity
public class Passenger extends Person {
    private int balance;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    private List<Trip> trips = new ArrayList<>();

    public Passenger(String name, String family, String username, String phoneNumber, long nationalCode, Date birthDate, TripStatus status, int balance) {
        super(name, family, username, phoneNumber, nationalCode, birthDate, status);
        this.balance = balance;
    }

    public Passenger() {
    }

    @Override
    public String toString() {
        return super.toString() + "," +
                "balance=" + balance;
    }
}
