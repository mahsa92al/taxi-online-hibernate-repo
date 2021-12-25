package model;

import enumeration.TripStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mahsa Alikhani m-58
 */
@Data
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String family;
    private String username;
    private String phoneNumber;
    private long nationalCode;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private TripStatus status;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Trip> trips = new ArrayList<>();

    public Person(String name, String family, String username, String phoneNumber, long nationalCode, Date birthDate, TripStatus status) {
        this.name = name;
        this.family = family;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.nationalCode = nationalCode;
        this.birthDate = birthDate;
        this.status = status;
    }

    public Person() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && nationalCode == person.nationalCode && Objects.equals(username, person.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, nationalCode);
    }
}