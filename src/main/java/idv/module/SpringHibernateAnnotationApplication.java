package idv.module;

import idv.module.config.AppConfig;
import idv.module.entity.Product;
import idv.module.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * * SpringHibernateAnnotationApplication. 2020/3/22 4:26 下午
 * *
 * * @author sero
 * * @version 1.0.0
 **/
public class SpringHibernateAnnotationApplication {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    public static void main(String[] args) throws InterruptedException {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductService productService = (ProductService) context.getBean("productService");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String insertProductName = "Test Product";

        /* get all Product */
        LOGGER.info("==============get all Product==============");
        List<Product> products = productService.findAll();
        for (Product p : products) {
            LOGGER.log(Level.INFO, "(SelectProduct) Id: {0}, EnName: {1}, ZhName: {2}, Price: {3}, ReleaseDate: {4}", new Object[]{p.getId(), p.getEnName(), p.getZhName(), p.getPrice(), sdf.format(p.getReleaseDate())});
        }
        Thread.sleep(50);

        /* select random Product */
        int productId = ThreadLocalRandom.current().nextInt(1, products.size() + 1);
        LOGGER.log(Level.INFO, "==============select {0} Product==============", productId);
        Product selectProduct = productService.findById(productId);
        LOGGER.log(Level.INFO, "(SelectProduct) Id: {0}, EnName: {1}, ZhName: {2}, Price: {3}, ReleaseDate: {4}", new Object[]{selectProduct.getId(), selectProduct.getEnName(), selectProduct.getZhName(), selectProduct.getPrice(), sdf.format(selectProduct.getReleaseDate())});
        Thread.sleep(50);

        /* insert */
        LOGGER.info("==============insert Test Product==============");
        Product insertProduct = new Product();
        insertProduct.setEnName(insertProductName);
        insertProduct.setZhName("測試商品");
        insertProduct.setPrice(9999.55);
        insertProduct.setReleaseDate(Timestamp.from(Instant.now()));
        productService.save(insertProduct);
        insertProduct = productService.findByEnNameLike(insertProductName).get(0);
        LOGGER.log(Level.INFO, "(InsertProduct) Id: {0}, EnName: {1}, ZhName: {2}, Price: {3}, ReleaseDate: {4}", new Object[]{insertProduct.getId(), insertProduct.getEnName(), insertProduct.getZhName(), insertProduct.getPrice(), sdf.format(insertProduct.getReleaseDate())});
        Thread.sleep(50);

        /* update */
        LOGGER.info("==============update Test Product==============");
        LOGGER.log(Level.INFO, "(Before insertProduct) ZhName: {0}, Price: {1}, EditDate: {2}", new Object[]{insertProduct.getZhName(), insertProduct.getPrice(), insertProduct.getEditDate() == null ? "" : sdf.format(insertProduct.getEditDate())});
        insertProduct.setPrice(4990.72);
        insertProduct.setZhName("測試商品更新");
        insertProduct.setEditDate(Timestamp.valueOf(LocalDateTime.now()));
        productService.update(insertProduct);
        Product updateProduct = productService.findById(insertProduct.getId());
        LOGGER.log(Level.INFO, "(After insertProduct) ZhName: {0}, Price: {1}, EditDate: {2}", new Object[]{updateProduct.getZhName(), updateProduct.getPrice(), updateProduct.getEditDate() == null ? "" : sdf.format(insertProduct.getEditDate())});
        Thread.sleep(50);

        /* delete */
        LOGGER.info("==============delete Test Product==============");
        List<Product> deleteProducts = productService.findByEnNameLike(insertProductName);
        for (Product p : deleteProducts) {
            LOGGER.log(Level.INFO, "(DeleteProduct) Id: {0}, EnName: {1}, ZhName: {2}, Price: {3}, ReleaseDate: {4}", new Object[]{p.getId(), p.getEnName(), p.getZhName(), p.getPrice(), sdf.format(p.getReleaseDate())});
            productService.deleteById(p.getId());
        }
        LOGGER.info("(After delete Test Product) end.");

        context.close();

    }

}
