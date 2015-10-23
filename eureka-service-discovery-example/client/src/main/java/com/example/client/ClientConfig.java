package com.example.client;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    private static final String ZK_HOST = "localhost";

//    @Bean(initMethod = "start", destroyMethod = "close")
//    public CuratorFramework curator() {
//        return CuratorFrameworkFactory.newClient(ZK_HOST, new ExponentialBackoffRetry(1000, 3));
//    }

//    @Bean(initMethod = "start", destroyMethod = "close")
//    public ServiceDiscovery<RestServiceDetails> discovery() {
//        return ServiceDiscoveryBuilder.builder(RestServiceDetails.class)
//                .client(curator())
//                .basePath("services")
//                .serializer(new JsonInstanceSerializer<>(RestServiceDetails.class))
//                .build();
//    }
}
