package io.rescrypt.batch.job.practice.batch_job_practice.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobExecutionListenerImpl implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(String.format("Batch Job (%s) has started successfully.", jobExecution.getJobInstance().getJobName()));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info(String.format("Batch Job (%s) has completed successfully.", jobExecution.getJobInstance().getJobName()));
    }
}
