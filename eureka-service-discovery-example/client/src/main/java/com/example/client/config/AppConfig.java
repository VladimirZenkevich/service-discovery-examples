package com.example.client.config;

import com.example.client.JaxRsApiApplication;
import com.example.client.rest.ClientEndpoint;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.collect.Lists;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.ws.rs.ext.RuntimeDelegate;

@Configuration
public class AppConfig {
    public static final String SERVER_PORT = "server.port";
    public static final String SERVER_HOST = "server.host";
    public static final String CONTEXT_PATH = "rest";

    @Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    @DependsOn("cxf")
    public Server jaxRsServer() {
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance()
                .createEndpoint(jaxRsApiApplication(), JAXRSServerFactoryBean.class);
        factory.setServiceBeans(Lists.<Object>newArrayList(clientEndpoint()));
        factory.setAddress(factory.getAddress());
        factory.setProviders(Lists.newArrayList(jsonProvider()));

        return factory.create();
    }

    @Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }

    @Bean
    public ClientEndpoint clientEndpoint() {
        return new ClientEndpoint();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }
}
