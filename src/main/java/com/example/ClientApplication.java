package com.example.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ClientApplication {

	@Autowired
	private EurekaClient client;
    @Autowired
	private RestTemplateBuilder restTemplateBuilder;
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@RequestMapping(name = "/")
	public String callService(){

		InstanceInfo instanceInfo=client.getNextServerFromEureka("service",false);
		String baseUrl=instanceInfo.getHomePageUrl();

		RestTemplate restTemplate=restTemplateBuilder.build();
	/*	ResponseEntity<String> response=restTemplate.exchange(baseUrl, HttpMethod.GET,null,String.class);
		return response.getBody();*/
	System.out.println("baseUrl-----"+baseUrl);
	   return  restTemplate.getForObject(baseUrl,String.class);
	}
}
