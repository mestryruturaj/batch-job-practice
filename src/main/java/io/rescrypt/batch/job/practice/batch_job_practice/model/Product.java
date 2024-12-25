package io.rescrypt.batch.job.practice.batch_job_practice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    private int productId;
    private String title;
    private String description;
    private Double price;
    private Double discount;
    @Transient
    private Double discountedPrice;
}
