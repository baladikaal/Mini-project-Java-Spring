package com.baladika.baladikaAPI.service;


import com.baladika.baladikaAPI.entity.JobApiResponseList;
import com.baladika.baladikaAPI.entity.JobEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    private final String apiUrl = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";

    public List<JobEntity> getJobsFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        JobEntity[] response = restTemplate.getForObject(apiUrl, JobEntity[].class);

        return Arrays.asList(response);
    }


    private final String jobDetailApiUrl = "http://dev3.dansmultipro.co.id/api/recruitment/positions/{id}";

    public JobEntity getJobDetailFromApi(String id, String jwtToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JobEntity> response = restTemplate.exchange(jobDetailApiUrl, HttpMethod.GET, entity, JobEntity.class, id);

        return response.getBody();
    }

}


