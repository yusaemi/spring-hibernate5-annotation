package idv.module.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * * HibernateConfig. 2020/3/22 4:12 下午
 * *
 * * @author sero
 * * @version 1.0.0
 **/
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@ComponentScan("idv.module.config")
@PropertySource("classpath:application.properties")
public class HibernateConfig {

    // 原本該配置的xml如hibernate-config.xml

    /**
     * @Configuration
     * 替代hibernate的xml配置，該類包含了用@Bean註釋的方法
     * 被@Bean註釋的方法可以生成bean交由Spring管理(如同xml的<bean.../>)
     *
     * @EnableTransactionManagement
     * 與xml配置中Spring的tx:*命名一樣，用途在於開啟註釋
     *
     * @ComponentScan
     * 與xml的context:component-scan base-package="..."一樣
     * 提供啟動掃描bean的package路徑。
     *
     * @PropertySource
     * 在Spring啟動時，將此路徑文件載入Environment類別(指定參照類別)
     *
     */


    /**
     * 將application.properties的屬性載入
     * 預設讀取resources的application.properties
     * 可以在%TOMCAT_HOME%/conf/catalina.properties文件中加一個屬性配置spring.profiles.active
     * spring會根據application-{profile}.properties參照相對映的properties
     * 如果除了找不到application.properties、也沒有配置active，就會預設載入application-default.properties
     */
    private final Environment env;

    /*
     * sessionFactory建立了一個LocalSessionFactoryBean，與xml相同，
     * 需要一個資料來源dataSource和hibernate配置文件（如同hibernate.properties，現在寫在這裡加上bean註釋）。
     * 使用@PropertySource註釋，可以從.properties文件中取得屬性，使用Spring的Environment接口取得對應項目的配置。
     * 一旦建立了SessionFactory，該bean將會被注入到transactionManager方法中
     * 對sessionFactory建立的sessions提供事務支持。
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("idv.module.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    // 自動注入相關的bean(SessionFactory)
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.hbm2ddl.import_files", env.getRequiredProperty("hibernate.hbm2ddl.import_files"));
        return properties;
    }

}
