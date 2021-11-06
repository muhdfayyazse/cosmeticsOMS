package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STOCK")
    private Integer stock;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "DISCOUNT")
    private Double discount;

    @Column(name = "RATING")
    private Integer rating;


    @Column(name = "SALE_PRICE")
    private double salePrice;

    @Column(name = "CATEGORY_ID")
    private String categoryId;

    @Column(name = "IS_DELETED")
    private Boolean deleted;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

}
