package idv.module;

import idv.module.config.AppConfig;
import idv.module.entity.Product;
import idv.module.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * * Application. 2020/3/22 4:26 下午
 * *
 * * @author sero
 * * @version 1.0.0
 *
 **/
public class SpringHibernateAnnotationApplication {

    public static void main(String[] args) throws InterruptedException {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductService productService = (ProductService) context.getBean("productService");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /* get all Product */
        System.err.println("==============get all Product==============");
        List<Product> products = productService.findAll();
        for (Product p : products) {
            System.out.printf("(SelectProduct) Id: %d, EnName: %s, ZhName: %s, Price: %f, ReleaseDate: %s\n", p.getId(), p.getEnName(), p.getZhName(), p.getPrice(), sdf.format(p.getReleaseDate()));
        }
        Thread.sleep(50);

        /* select random Product */
        int productId = ThreadLocalRandom.current().nextInt(1, products.size() + 1);
        System.err.printf("==============select %d Product==============\n", productId);
        Product selectProduct = productService.findById(productId);
        System.out.printf("(SelectProduct) Id: %d, EnName: %s, ZhName: %s, Price: %f, ReleaseDate: %s\n", selectProduct.getId(), selectProduct.getEnName(), selectProduct.getZhName(), selectProduct.getPrice(), sdf.format(selectProduct.getReleaseDate()));
        Thread.sleep(50);

        /* insert */
        System.err.println("==============insert Test Product==============");
        Product insertProduct = new Product();
        insertProduct.setEnName("Test Product");
        insertProduct.setZhName("測試商品");
        insertProduct.setPrice(9999.55);
        insertProduct.setReleaseDate(Timestamp.from(Instant.now()));
        productService.save(insertProduct);
        insertProduct = productService.findByEnNameLike("Test Product").get(0);
        System.out.printf("(InsertProduct) Id: %d, EnName: %s, ZhName: %s, Price: %f, ReleaseDate: %s\n", insertProduct.getId(), insertProduct.getEnName(), insertProduct.getZhName(), insertProduct.getPrice(), sdf.format(insertProduct.getReleaseDate()));
        Thread.sleep(50);

        /* update */
        System.err.println("==============update Test Product==============");
        System.out.printf("(Before insertProduct) ZhName: %s, Price: %f, EditDate: %s\n", insertProduct.getZhName(), insertProduct.getPrice(), (insertProduct.getEditDate() == null ? "" : sdf.format(insertProduct.getEditDate())));
        insertProduct.setPrice(4990.72);
        insertProduct.setZhName("測試商品更新");
        insertProduct.setEditDate(Timestamp.valueOf(LocalDateTime.now()));
        productService.update(insertProduct);
        Product updateProduct = productService.findById(insertProduct.getId());
        System.out.printf("(After insertProduct) ZhName: %s, Price: %f, EditDate: %s\n", updateProduct.getZhName(), updateProduct.getPrice(), (updateProduct.getEditDate() == null ? "" : sdf.format(insertProduct.getEditDate())));
        Thread.sleep(50);

        /* delete */
        System.err.println("==============delete Test Product==============");
        List<Product> deleteProducts = productService.findByEnNameLike("Test Product");
        for (Product p : deleteProducts) {
            System.out.printf("(DeleteProduct) Id: %d, EnName: %s, ZhName: %s, Price: %f, ReleaseDate: %s\n", p.getId(), p.getEnName(), p.getZhName(), p.getPrice(), sdf.format(p.getReleaseDate()));
            productService.deleteById(p.getId());
        }
        System.out.println("(After delete Test Product) end.");

        context.close();

    }

}
