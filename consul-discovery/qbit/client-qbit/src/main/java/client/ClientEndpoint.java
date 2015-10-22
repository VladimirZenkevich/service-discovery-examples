package client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientEndpoint {

    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String check() {
//        Consul consul = Consul.newClient("localhost", 9500); // connect to Consul on localhost
//        HealthClient healthClient = consul.healthClient();
//
//        List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("MyService").getResponse(); // discover only "passing" nodes

        String response = "";
//        for (ServiceHealth serviceHealth : nodes) {
//            response = response + serviceHealth.getService().getService() + " " + serviceHealth.getService().getId() + "; ";
//        }

        return response;
    }

}
