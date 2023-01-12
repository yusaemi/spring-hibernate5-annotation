package idv.module.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * * AbstractDao. 2020/3/22 3:59 下午
 * *
 * * @author sero
 * * @version 1.0.0
 *
 **/
@RequiredArgsConstructor
public class AbstractDao {

    private final SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void persist(Object entity) {
        getSession().persist(entity);
    }

    public void delete(Object entity) {
        getSession().remove(entity);
    }

}
