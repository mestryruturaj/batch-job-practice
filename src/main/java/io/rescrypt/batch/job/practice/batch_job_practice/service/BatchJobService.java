package io.rescrypt.batch.job.practice.batch_job_practice.service;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
public class BatchJobService {
    private final JobLauncher jobLauncher;
    private final Job calculateDiscountedPriceJob;

    public BatchJobService(JobLauncher jobLauncher, Job calculateDiscountedPriceJob) {
        this.jobLauncher = jobLauncher;
        this.calculateDiscountedPriceJob = calculateDiscountedPriceJob;
    }

    public BatchStatus performCalculateDiscountedPriceBatchJob() {
        try {
            jobLauncher.run(calculateDiscountedPriceJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
            return BatchStatus.COMPLETED;
        } catch (Exception exception) {
            return BatchStatus.FAILED;
        }
    }
}
