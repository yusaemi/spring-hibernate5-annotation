package idv.module.repository;

import idv.module.entity.Product;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * * ProductDao. 2020/3/22 4:00 下午
 * *
 * * @author sero
 * * @version 1.0.0
 *
 **/
// 是用來给持久層的類別定義一個名字，讓Spring根據名子關連到這個類別。(宣告了ProductDao在Spring容器中叫productDao這個名子)
@Repository("productDao")
public class ProductDao extends AbstractDao {

    public ProductDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(Product product) {
        persist(product);
    }

    public List<Product> findAll() {

        // deprecated
//        Criteria criteria = getSession().createCriteria(Product.class);
//        return (List<Product>) criteria.list();

        CriteriaQuery<Product> criteriaQuery = getSession().getCriteriaBuilder().createQuery(Product.class);
        criteriaQuery.from(Product.class);
        Query<Product> query = getSession().createQuery(criteriaQuery);
        return query.getResultList();
    }

    public void deleteById(Integer id) {
        Query query = getSession().createSQLQuery("delete from product where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<Product> findByEnNameLike(String enName) {

        // deprecated
//        Criteria criteria = getSession().createCriteria(Product.class);
//        criteria.add(Restrictions.like("name", "%" + name + "%"));
//        return (Product) criteria.uniqueResult();

        CriteriaBuilder criteriaBuilder = getSession().getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root).where(criteriaBuilder.like(root.get("enName"), "%" + enName + "%"));
        Query<Product> query = getSession().createQuery(criteriaQuery);
        return query.getResultList();
    }

    public Product findById(Integer id) {

        // deprecated
//        Criteria criteria = getSession().createCriteria(Product.class);
//        criteria.add(Restrictions.eq("id", id));
//        return (Product) criteria.uniqueResult();

        CriteriaBuilder criteriaBuilder = getSession().getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        Query<Product> query = getSession().createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    public void update(Product product) {
        getSession().update(product);
    }

}
