package io.rescrypt.batch.job.practice.batch_job_practice.config.batch;

import io.rescrypt.batch.job.practice.batch_job_practice.model.Product;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {
    @Bean(value = "calculateDiscountedPricesJob")
    public Job calculateDiscountedPriceJob(JobRepository jobRepository,
                                           JobExecutionListenerImpl jobExecutionListener,
                                           @Qualifier("calculateDiscountedPricesStep") Step calculateDiscountedPricesStep) {
        return new JobBuilder("calculateDiscountedPriceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener)
                .start(calculateDiscountedPricesStep)
                .build();
    }

    @Bean(value = "calculateDiscountedPricesStep")
    public Step calculateDiscountedPriceStep(JobRepository jobRepository,
                                             PlatformTransactionManager transactionManager,
                                             ItemReader<Product> itemReader,
                                             ItemProcessor<Product, Product> itemProcessor,
                                             ItemWriter<Product> itemWriter) {
        return new StepBuilder("calculateDiscountedPriceStep", jobRepository)
                .<Product, Product>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public ItemReader<Product> itemReader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("itemReader")
                .resource(new ClassPathResource("static/dummy_product_data.csv"))
                .delimited()
                .names("productId", "title", "description", "price", "discount")
                .targetType(Product.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public ItemProcessor<Product, Product> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<Product> itemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("insert into product(title,description,price,discount,discounted_price) values(:title,:description,:price,:discount,:discountedPrice)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
}
