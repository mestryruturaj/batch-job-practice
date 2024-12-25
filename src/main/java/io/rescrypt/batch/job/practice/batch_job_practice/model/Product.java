package io.rescrypt.batch.job.practice.batch_job_practice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private String title;

    private String description;

    private Double price;

    private Double discount;

    private Double discountedPrice;
}
