package idv.module.service;

import idv.module.entity.Product;
import idv.module.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * * ProductService. 2020/3/22 4:06 下午
 * *
 * * @author sero
 * * @version 1.0.0
 *
 **/
// Service用於服務層的類別文件，功能與@Repository類似(宣告了ProductService在Spring容器中叫productService這個名子)
@Service("productService")
// 表示將這項任務交給 spring 處理，spring會以AOP在方法結束時進行commit，如果有錯，則自動進行roll back。
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public void save(Product product) {
        productDao.save(product);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void deleteById(Integer id) {
        productDao.deleteById(id);
    }

    public List<Product> findByEnNameLike(String enName) {
        return productDao.findByEnNameLike(enName);
    }

    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    public void update(Product product) {
        productDao.update(product);
    }

}
