package com.baladika.baladikaAPI.service;


import com.baladika.baladikaAPI.entity.JobApiResponseList;
import com.baladika.baladikaAPI.entity.JobEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class JobService {

    private final String apiUrl = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";

    public List<JobEntity> getJobsFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        JobEntity[] response = restTemplate.getForObject(apiUrl, JobEntity[].class);

        return Arrays.asList(response);
    }
}


