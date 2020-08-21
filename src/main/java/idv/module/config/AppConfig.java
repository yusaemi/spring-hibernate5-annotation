package idv.module.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * * AppConfig. 2020/3/22 4:08 下午
 * *
 * * @author sero
 * * @version 1.0.0
 *
 **/
@Configuration
@ComponentScan(basePackages = "idv.module")
public class AppConfig {

    // 原本該配置的xml如application-config.xml

    /**
     * 使用了@ComponentScan註釋，自動掃描對應package底下的所有beans。
     * 也可以不使用這個class，將bean掃描功能放在application context級別去實做(main方法里)。
     * 使用配置類別配置beans（如messageSource、PropertySourcesPlaceHolderConfigurer...）很方便
     *
     * config完成後，接下來要配置service、dao、entity、database
     */

}