package com.baladika.baladikaAPI.controller;

import com.baladika.baladikaAPI.entity.JobEntity;
import com.baladika.baladikaAPI.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment/positions")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobEntity>> getJobs() {
        List<JobEntity> jobs = jobService.getJobsFromApi();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobEntity> getJobDetail(@PathVariable String id, @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.substring(7);

        JobEntity jobDetail = jobService.getJobDetailFromApi(id, jwtToken);
        if (jobDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobDetail);
    }

}



