package io.rescrypt.batch.job.practice.batch_job_practice.config.batch;

import io.rescrypt.batch.job.practice.batch_job_practice.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class BatchConfig {
    /**
     * 1. Batch job to read csv, calculate discounted price, insert into db
     * 2. When batch job starts I want to log
     * 3. When batch job ends I want to log
     * 4. I want batch job to run when an end point is hit
     */

    @Bean("calculateDiscountedPriceJob")
    public Job calculateDiscountedPriceJob(JobRepository jobRepository, JobExecutionListenerImpl jobExecutionListener, Step calculateDiscountedPriceStep) {
        return new JobBuilder("calculateDiscountedPriceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener)
                .start(calculateDiscountedPriceStep)
                .build();
    }

    @Bean("calculateDiscountedPriceStep")
    public Step calculateDiscountedPriceStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, ItemReader<Product> itemReader,
                                             ItemProcessor<Product, Product> itemProcessor, ItemWriter<Product> itemWriter) {
        return new StepBuilder("calculateDiscountedPriceStep", jobRepository)
                .<Product, Product>chunk(10, platformTransactionManager)
                .faultTolerant()
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public ItemReader<Product> itemReader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("ItemReader")
                .resource(new ClassPathResource("static/dummy_product_data.csv"))
                .delimited()
                .delimiter(",")
                .names("productId", "title", "description", "price", "discount")
                .targetType(Product.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public ItemProcessor<Product, Product> itemProcessor() {
        return new ItemProcessor<Product, Product>() {
            @Override
            public Product process(Product item) throws Exception {
                double price = item.getPrice();
                double discount = item.getDiscount();
                ;
                double discountedPrice = price * (100 - discount) / 100;

                item.setDiscountedPrice(discountedPrice);
                return item;
            }
        };
    }

    @Bean
    public ItemWriter<Product> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .dataSource(dataSource)
                .sql("insert into product(title, description, price, discount, discountedPrice) values(:title, :description, :price, :discount, :discountedPrice);")
                .beanMapped()
                .build();
    }
}
