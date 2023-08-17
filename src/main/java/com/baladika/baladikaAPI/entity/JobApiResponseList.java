package com.baladika.baladikaAPI.entity;

import java.util.List;

public class JobApiResponseList {
    private List<JobEntity> jobs;

    public List<JobEntity> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobEntity> jobs) {
        this.jobs = jobs;
    }
}

