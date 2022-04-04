package idv.module.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * * Product. 2020/3/22 1:05 上午
 * *
 * * @author sero
 * * @version 1.0.0
 *
 **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "en_name", nullable = false)
    private String enName;

    @Column(name = "zh_name")
    private String zhName;

    @Column(name = "price", nullable = false)
    private Double price;

    // 使用joda-time處理資料庫時間
    @Column(name = "release_date", nullable = false)
    private Timestamp releaseDate;

    // usertype-core導入是為了提供資料庫時間類型與joda-time LocalDate之間的對應。
    @Column(name = "edit_date")
    private Timestamp editDate;

}
