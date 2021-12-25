package dataAccess;

import enumeration.PayStatus;
import model.Driver;
import model.Passenger;
import model.Trip;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import java.util.List;

/**
 * @author Mahsa Alikhani m-58
 */
public class TripDataAccess extends DataBaseAccess{

    public Trip findTripByDriver(Driver driver) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Trip> hql = session.createQuery("from Trip t where t.driver=:driver");
        hql.setParameter("driver", driver);
        Trip trip = hql.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return trip;
    }
    public Passenger findPassengerByDriverAndTripId(Driver driver, int tripId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Passenger> hql = session.createQuery("from Trip t where t.driver=:driver and t.id=:id");
        hql.setParameter("driver", driver);
        hql.setParameter("id", tripId);
        Passenger passenger = hql.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return passenger;
    }
    public void updateTripPayStatusAfterPaying(Trip trip) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(trip);
        session.getTransaction().commit();
        session.close();
    }

    public void saveTrip(Trip trip) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(trip);
        session.getTransaction().commit();
        session.close();
    }

    public List<Trip> getOngoingTravels()  {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Trip.class);
        criteria.add(Restrictions.or(Restrictions.eq("payStatus", PayStatus.ACCOUNT),
                Restrictions.eq("payStatus", PayStatus.CASH)));
        List<Trip> trips = criteria.list();
        session.getTransaction().commit();
        session.close();
        return trips;
    }
}
