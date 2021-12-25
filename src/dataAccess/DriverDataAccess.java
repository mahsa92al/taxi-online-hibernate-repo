package dataAccess;

import enumeration.TripStatus;
import model.Driver;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Mahsa Alikhani m-58
 */
public class DriverDataAccess extends DataBaseAccess {

    public void saveGroupOfDrivers(List<Driver> drivers) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Driver driver: drivers) {
            session.save(driver);
        }
        session.getTransaction().commit();
        session.close();
    }

    public Driver findDriverByUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Driver> hql = session.createQuery("from Driver d where d.username=:username");
        hql.setParameter("username", username);
        Driver foundDriver = hql.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return foundDriver;
    }

    public void UpdateDriverLocation(Driver driver) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(driver);
        session.getTransaction().commit();
        session.close();
    }

    public void updateDriverStatusToWaitByDriver(Driver driver) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(driver);
        session.getTransaction().commit();
        session.close();
    }
    public void updateDriverStatusToONGOING(Driver driver) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(driver);
        session.getTransaction().commit();
        session.close();
    }
    public List<Driver>findDriverByWaitStatus() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Driver.class);
        criteria.add(Restrictions.eq("status", TripStatus.WAIT));
        List<Driver> drivers = criteria.list();
        session.getTransaction().commit();
        session.close();
        return drivers;
    }

    public void saveNewDriver(Driver driver) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(driver);
        session.getTransaction().commit();
        session.close();
    }

    public void updateDriverLocation(Driver driver) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(driver);
        session.getTransaction().commit();
        session.close();
    }

    public List<Driver> getListOfDrivers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Driver> hql = session.createQuery("from Driver");
        List<Driver> drivers = hql.getResultList();
        session.getTransaction().commit();
        session.close();
        return drivers;
    }
}