package model;

import enumeration.PayStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * @author Mahsa Alikhani m-58
 */
@Data
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double originLat;
    private double originLong;
    private double destinationLat;
    private double destinationLong;
    private int price;
    @Temporal(TemporalType.DATE)
    private Date tripDate;
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;
    @ManyToOne
    private Passenger passenger;
    @ManyToOne
    private Driver driver;


    public Trip(double originLat, double originLong, double destinationLat, double destinationLong, int price, Date tripDate, PayStatus payStatus) {
        this.originLat = originLat;
        this.originLong = originLong;
        this.destinationLat = destinationLat;
        this.destinationLong = destinationLong;
        this.price = price;
        this.tripDate = tripDate;
        this.payStatus = payStatus;
    }

    public Trip() {
    }

    @Override
    public String toString() {
        return "Ongoing Trip\n" +
                "Origin: " + originLat + ", " + originLong +"\n" +
                "Destination: " + destinationLat + ", " + destinationLong + "\n" +
                "Price: " + price + "\n" +
                "Date: " + tripDate + "\n" +
                "Passenger ID: " + passenger.getId() + "\n" +
                "Driver ID: " + driver.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Double.compare(trip.originLat, originLat) == 0 && Double.compare(trip.originLong, originLong) == 0 && Double.compare(trip.destinationLat, destinationLat) == 0 && Double.compare(trip.destinationLong, destinationLong) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(originLat, originLong, destinationLat, destinationLong);
    }
}
