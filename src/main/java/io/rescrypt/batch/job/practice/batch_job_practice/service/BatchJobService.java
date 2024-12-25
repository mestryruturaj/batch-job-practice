package io.rescrypt.batch.job.practice.batch_job_practice.service;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BatchJobService {
    private final JobLauncher jobLauncher;

    private final Job calculateDiscountedPriceJob;

    public BatchJobService(JobLauncher jobLauncher, @Qualifier("calculateDiscountedPricesJob") Job calculateDiscountedPriceJob) {
        this.jobLauncher = jobLauncher;
        this.calculateDiscountedPriceJob = calculateDiscountedPriceJob;
    }

    public BatchStatus runCalculateDiscountedPriceJob() {
        try {
            JobExecution jobExecution = jobLauncher.run(calculateDiscountedPriceJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
            return BatchStatus.COMPLETED;
        } catch (Exception exception) {
            return BatchStatus.FAILED;
        }
    }

}
