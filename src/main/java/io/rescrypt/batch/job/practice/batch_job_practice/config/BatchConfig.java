package io.rescrypt.batch.job.practice.batch_job_practice.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    @Bean
    public Job calculateDiscountedPriceJob(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                           JobExecutionListenerImpl jobExecutionListener, @Qualifier("calculateDiscountedPricesStep") Step calculateDiscountedPricesStep) {
        return new JobBuilder(name = "calculateDiscountedPriceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(calculateDiscountedPricesStep)
                .build();
    }

    @Bean(value = "calculateDiscountedPricesStep")
    public Step calculateDiscountedPriceStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("calculateDiscountedPriceJob", jobRepository)
                .chunk(10, transactionManager)
                .reader()
    }
}
