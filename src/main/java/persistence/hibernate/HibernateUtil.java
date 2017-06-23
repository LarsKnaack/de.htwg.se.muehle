package persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * Created by Thomas on 22.06.2017.
 */
public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static  {
        final Configuration cfg = new
                Configuration();
                cfg.configure("hibernate.cfg.xml");
                SESSION_FACTORY = cfg.buildSessionFactory();

    }

    private HibernateUtil() {

    }

    public static SessionFactory getInstance() {
        return SESSION_FACTORY;
    }
}
