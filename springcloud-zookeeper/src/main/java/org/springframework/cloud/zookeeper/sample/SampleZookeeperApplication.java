package org.springframework.cloud.zookeeper.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.ServiceInstance;

/**
 * @author liuwang
 * @date 17/08/2022 4:36 PM
 */
@Configuration
@EnableAutoConfiguration
@RestController
@EnableFeignClients
public class SampleZookeeperApplication {

    @Value("${spring.application.name:testZookeeperApp}")
    private String appName;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discovery;

    @Autowired
    private Environment env;

    @Autowired
    private AppClient appClient;

    @Autowired(required = false)
    private Registration registration;

    private RestTemplate rest;

    @RequestMapping("/")
    public ServiceInstance lb() {
        return this.loadBalancer.choose(this.appName);
    }

    @RequestMapping("/hi")
    public String hi() {
        System.out.println("==>"+"Hello World!");
        return "Hello World! from " + this.registration;
    }

    @RequestMapping("/self")
    public String self() {
        return this.appClient.hi();
    }

    @RequestMapping("/myenv")
    public String env(@RequestParam("prop") String prop) {
        return this.env.getProperty(prop, "Not Found");
    }

    public String rt() {
        return this.rest.getForObject("http://" + this.appName + "/hi", String.class);
    }

    @Bean
    @LoadBalanced
    RestTemplate loadBalancedRestTemplate() {
        this.rest = new RestTemplateBuilder().build();
        return this.rest;
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleZookeeperApplication.class, args);
    }

    @FeignClient("testZookeeperApp")
    interface AppClient {

        @RequestMapping(path = "/hi", method = RequestMethod.GET)
        String hi();

    }

}
