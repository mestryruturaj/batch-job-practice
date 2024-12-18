package io.rescrypt.batch.job.practice.batch_job_practice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobExecutionListenerImpl implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(String.format("Batch job %s has started.", jobExecution.getJobInstance().getJobName()))
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            log.info(String.format("Batch job %s has completed.", jobExecution.getJobInstance().getJobName()))
        }
    }
}
