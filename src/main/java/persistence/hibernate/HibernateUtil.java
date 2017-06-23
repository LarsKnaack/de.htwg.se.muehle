package persistence.hibernate;

import org.hibernate.cfg.AnnotationInclusion;
import org.hibernate.SessionFactory;



/**
 * Created by Thomas on 22.06.2017.
 */
public final class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static  {
        final AnnotationConfiguration cfg = new
                AnnotationConfiguration();
                cfg.configure("hibernate.cfg.xml");
                SESSION_FACTORY = cfg.buildSessionFactory();

    }

    private HibernateUtil() {

    }

    public static SessionFactory getInstance() {
        return SESSION_FACTORY;
    }
}
