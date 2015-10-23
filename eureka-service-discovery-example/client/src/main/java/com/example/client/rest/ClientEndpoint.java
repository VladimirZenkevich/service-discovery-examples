package com.example.client.rest;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClient;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/check")
public class ClientEndpoint {

    @PostConstruct
    public void init() throws Exception {
        ApplicationInfoManager applicationInfoManager = ApplicationInfoManager.getInstance();

        DiscoveryManager.getInstance().initComponent(
                new MyDataCenterInstanceConfig(),
                new DefaultEurekaClientConfig());

        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String check() {
        // this is the vip address for the example service to talk to as defined in conf/sample-eureka-service.properties
        String vipAddress = "sampleservice.mydomain.net";

        InstanceInfo nextServerInfo = null;
        try {
            nextServerInfo = DiscoveryManager.getInstance()
                    .getEurekaClient()
                    .getNextServerFromEureka(vipAddress, false);
        } catch (Exception e) {
            System.err.println("Cannot get an instance of example service to talk to from eureka");
            System.exit(-1);
        }

//        Response response = ClientBuilder.newClient()
//                .target(nextServerInfo.getHomePageUrl() + "rest/api/people")
//                .request(MediaType.APPLICATION_JSON)
//                .get();

//        System.out.println(nextServerInfo.getHostName() + ": " + response.readEntity(String.class));

//        response.close();

        System.out.println(nextServerInfo.getHomePageUrl());
        return nextServerInfo.getHomePageUrl();
//        return nextServerInfo.getHostName() + ": " + response.readEntity(String.class);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/stop")
    public String stop1() {

        EurekaClient eurekaClient = DiscoveryManager.getInstance().getEurekaClient();

        if (eurekaClient != null) {
            eurekaClient.shutdown();
        }

        return "Stopped";
    }

}
