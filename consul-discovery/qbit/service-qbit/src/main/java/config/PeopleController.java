package config;

import com.google.common.collect.Lists;
import io.advantageous.consul.discovery.ConsulServiceDiscoveryBuilder;
import io.advantageous.qbit.service.discovery.ServiceDiscovery;
import model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collection;


@RestController
public class PeopleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleController.class);

    private String serviceName = "MyService";
    private String serviceId = "demo_service_" + System.getProperty("server.port");
    private ServiceDiscovery clientAgent;

    @PostConstruct
    public void init() throws Exception {
        final ConsulServiceDiscoveryBuilder consulServiceDiscoveryBuilder =
                ConsulServiceDiscoveryBuilder.consulServiceDiscoveryBuilder().setDatacenter("dc2");

        clientAgent = consulServiceDiscoveryBuilder.setConsulPort(8500).build();

        clientAgent.start();

                /* Register the services. */
        clientAgent.registerWithIdAndTimeToLive(
                serviceName, serviceId,
                Integer.valueOf(System.getProperty("server.port")), 100000);
    }

    @RequestMapping(value = "/health",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String healthCheck() throws Exception {
//        Consul consul = Consul.newClient("localhost", 9500); // connect to Consul on localhost
//        AgentClient agentClient = consul.agentClient();
//
//        agentClient.pass(serviceId);

        return "Health";
    }

    @RequestMapping(value = "/people",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Person> getPeople() {
        LOGGER.info("!!!!!!!!!!!!!! Start retrieving person's data.");

        return Lists.newArrayList(
                new Person().setFirstName("John").setLastName("Bombadil"),
                new Person().setFirstName("Jim").setLastName("Tommyknockers")
        );
    }

    @RequestMapping(value = "/stop",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String stop1() {
        LOGGER.info("!!!!!!!!!!!!!! Stopping service discovery");

        clientAgent.stop();

        return "Stopped";
    }

}
