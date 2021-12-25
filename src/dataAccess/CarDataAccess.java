package dataAccess;

import model.Car;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Mahsa Alikhani m-58
 */
public class CarDataAccess extends DataBaseAccess{

    public void saveGroupOfCar(List<Car> cars) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Car car: cars) {
            session.save(car);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void saveNewCar(Car car) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(car);
        session.getTransaction().commit();
        session.close();
    }
}
