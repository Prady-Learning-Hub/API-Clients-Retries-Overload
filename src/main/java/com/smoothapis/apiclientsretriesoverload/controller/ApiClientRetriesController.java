package com.smoothapis.apiclientsretriesoverload.controller;

import com.smoothapis.apiclientsretriesoverload.service.ApiClientRetriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiClientRetriesController {
    @Autowired
    private ApiClientRetriesService apiClientRetriesService;

    @GetMapping("/api-call-with-retry")
    public String apiCallWithRetry(@RequestParam String uri){
        int delayBase = 500; //initial delay in ms
        int maxNoRetries = 4; //Max number of Retries
        try {
            return apiClientRetriesService.callApiWithRetries(uri,delayBase,maxNoRetries);
        } catch (Exception ex){
            return "API call Request failed : " + ex.getMessage();
        }
    }

}
