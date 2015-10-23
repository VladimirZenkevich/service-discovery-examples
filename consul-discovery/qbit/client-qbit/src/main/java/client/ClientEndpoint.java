package client;

import io.advantageous.consul.discovery.ConsulServiceDiscoveryBuilder;
import io.advantageous.qbit.service.discovery.EndpointDefinition;
import io.advantageous.qbit.service.discovery.ServiceDiscovery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientEndpoint {

    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String check() {

        final ConsulServiceDiscoveryBuilder consulServiceDiscoveryBuilder =
                ConsulServiceDiscoveryBuilder.consulServiceDiscoveryBuilder();

        final ServiceDiscovery clientAgent = consulServiceDiscoveryBuilder
                .setDatacenter("dc2")
                .build();

        List<EndpointDefinition> myService = clientAgent.loadServicesNow("MyService");
        String response = "";
        for (EndpointDefinition service : myService) {
            response = response + service.getId() + " " + service.getHost() + " " + service.getPort() + " " + service.getHealthStatus().name() + ";\n";
        }

        return response;
    }


    @RequestMapping(value = "/check_dc1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String check1() {

        final ConsulServiceDiscoveryBuilder consulServiceDiscoveryBuilder =
                ConsulServiceDiscoveryBuilder.consulServiceDiscoveryBuilder();

        final ServiceDiscovery clientAgent = consulServiceDiscoveryBuilder
                .setDatacenter("dc1")
                .build();


        List<EndpointDefinition> myService = clientAgent.loadServicesNow("myservice");

        String response = "";
        for (EndpointDefinition service : myService) {
            response = response + service.getId() + " " + service.getHost() + " " + service.getPort() + " " + service.getHealthStatus().name() + ";\n";
        }

        return response;
    }

}
