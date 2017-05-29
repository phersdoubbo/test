package com.scholastic.reference.restmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

@SpringBootApplication (scanBasePackages="com.scholastic.reference.restmicroservice")
public class Boot {

    @Bean
    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public JmxReporter jmxReporter(MetricRegistry metricRegistry) {
        return JmxReporter.forRegistry(metricRegistry).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }

}
