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
public class Driver extends Person{
    private String plaque;
    private double currentLocationLat;
    private double currentLocationLong;
    @OneToOne
    private Car car;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    private List<Trip> trips = new ArrayList<>();
}
