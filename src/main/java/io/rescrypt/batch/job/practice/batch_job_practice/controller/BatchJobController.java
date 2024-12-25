package io.rescrypt.batch.job.practice.batch_job_practice.controller;

import io.rescrypt.batch.job.practice.batch_job_practice.service.BatchJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping(value = "/batch-job")
public class BatchJobController {
    private final BatchJobService batchJobService;

    @GetMapping(value = "/run")
    public ResponseEntity<String> performProductBatchJob() {
        BatchStatus batchStatus = batchJobService.runCalculateDiscountedPriceJob();
        if (BatchStatus.COMPLETED.equals(batchStatus)) {
            return new ResponseEntity<String>("Batch Job Successfully completed.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Batch Job was not completed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
