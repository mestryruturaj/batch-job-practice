package io.rescrypt.batch.job.practice.batch_job_practice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private int productId;
    private String title;
    private String description;
    private Double price;
    private Double discount;
    private Double discountedPrice;
}
