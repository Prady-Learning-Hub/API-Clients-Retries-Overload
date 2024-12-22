package com.smoothapis.apiclientsretriesoverload.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Random;


@Service
public class ApiClientRetriesService {
    private final RestTemplate restTemplate;
    private final Random random;

    public ApiClientRetriesService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
        this.random = new Random();
    }

    public String callApiWithRetries(String uri, int delayBase, int maxNoRetries) throws Exception{
        String response;
        for (int retryCount = 0; retryCount < maxNoRetries; retryCount++) {
            try {
                response = restTemplate.getForObject(uri, String.class);
                return response;
            }catch(Exception ex){
                //Evaluate exponential back off delay with base 2
                int delayVal = (int) (delayBase * (Math.pow(2,retryCount)));
                // jitter is Random delay beyween 50% and 100% of the delayVal
                int jitterVal = (int) (delayVal * (0.5 + random.nextDouble() * 0.5));
                System.out.println("Retry Count = " + (retryCount + 1) + ": In wait for " + jitterVal + " ms before retry.");
                Thread.sleep(jitterVal);
            }
        }
        throw new Exception("Maximum number of Retries exceeded  : " + uri);
    }
}
