package com.example.client;

import com.example.client.config.AppConfig;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ClientStarter {

    public static void main(final String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Please provide port number");
            return;
        }

        final int port = Integer.valueOf(args[0]);
        final Server server = new Server(port);

        System.setProperty(AppConfig.SERVER_PORT, Integer.toString(port));
        System.setProperty(AppConfig.SERVER_HOST, "localhost");

        // Register and map the dispatcher servlet
        server.setHandler(getServletContextHandler());
        server.start();
        server.join();
    }

    private static ServletContextHandler getServletContextHandler() {
        final ServletContextHandler context = new ServletContextHandler();

        context.setContextPath("/");
        context.addServlet(new ServletHolder(new CXFServlet()), "/" + AppConfig.CONTEXT_PATH + "/*");
        context.addEventListener(new ContextLoaderListener());
        context.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
        context.setInitParameter("contextConfigLocation", AppConfig.class.getName());

        return context;
    }

//    public static void main(final String[] args) throws Exception {
//
//        ApplicationInfoManager applicationInfoManager = ApplicationInfoManager.getInstance();
//
//        DiscoveryManager.getInstance().initComponent(
//                new MyDataCenterInstanceConfig(),
//                new DefaultEurekaClientConfig());
//
//        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
//
//        // this is the vip address for the example service to talk to as defined in conf/sample-eureka-service.properties
//        String vipAddress = "sampleservice.mydomain.net";
//
//        InstanceInfo nextServerInfo = null;
//        try {
//            nextServerInfo = DiscoveryManager.getInstance()
//                    .getEurekaClient()
//                    .getNextServerFromEureka(vipAddress, false);
//        } catch (Exception e) {
//            System.err.println("Cannot get an instance of example service to talk to from eureka");
//            System.exit(-1);
//        }
//
//        System.out.println("Found an instance of example service to talk to from eureka: " + nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());
//        System.out.println("healthCheckUrl: " + nextServerInfo.getHealthCheckUrl());
//        System.out.println("override: " + nextServerInfo.getOverriddenStatus());
//
//
//        Response response = ClientBuilder.newClient()
//                .target(nextServerInfo.getHomePageUrl() + "rest/api/people")
//                .request(MediaType.APPLICATION_JSON)
//                .get();
//
//        System.out.println(nextServerInfo.getHostName() + ": " + response.readEntity(String.class));
//
//        response.close();
//
//
//        // finally shutdown
////        DiscoveryManager.getInstance().getEurekaClient().shutdown();
//
//
////        }
//    }

}
