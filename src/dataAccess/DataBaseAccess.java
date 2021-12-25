package dataAccess;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Mahsa Alikhani m-58
 */
public class DataBaseAccess {
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
}
