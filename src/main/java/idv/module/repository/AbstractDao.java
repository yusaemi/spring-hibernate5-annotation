package idv.module.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * * AbstractDao. 2020/3/22 3:59 下午
 * *
 * * @author sero
 * * @version 1.0.0
 *
 **/
public class AbstractDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void persist(Object entity) {
        getSession().persist(entity);
    }

    public void delete(Object entity) {
        getSession().delete(entity);
    }

}
