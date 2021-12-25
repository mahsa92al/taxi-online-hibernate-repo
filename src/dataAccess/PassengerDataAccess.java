package dataAccess;

import model.Passenger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Mahsa Alikhani m-58
 */
public class PassengerDataAccess extends DataBaseAccess {

    public void saveGroupOfPassengers(List<Passenger> passengers) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Passenger passenger: passengers) {
            session.save(passenger);
        }
        session.getTransaction().commit();
        session.close();
    }

    public Passenger findPassengerByUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Passenger> hql = session.createQuery("from Passenger p where p.username=:username");
        hql.setParameter("username", username);
        Passenger passenger = hql.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return passenger;
    }
    public void saveNewPassenger(Passenger passenger) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(passenger);
        session.getTransaction().commit();
        session.close();
    }

    public void increaseBalance(Passenger passenger) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(passenger);
        session.getTransaction().commit();
        session.close();
    }
    public void decreasePassengerBalance(Passenger passenger) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(passenger);
        session.getTransaction().commit();
        session.close();
    }

    public void updatePassengerStatusToONGOING(Passenger passenger) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(passenger);
        session.getTransaction().commit();
        session.close();
    }

    public void updatePassengerStatusToSTOP(Passenger passenger) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(passenger);
        session.getTransaction().commit();
        session.close();
    }

    public List<Passenger> getListOfPassengers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Passenger> hql = session.createQuery("from Passenger ");
        List<Passenger> passengers = hql.getResultList();
        session.getTransaction().commit();
        session.close();
        return passengers;
    }
}
