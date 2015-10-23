package com.example.rs;

import com.example.model.Person;
import com.google.common.collect.Lists;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path(PeopleRestService.PEOPLE_PATH)
public class PeopleRestService {
    public static final String PEOPLE_PATH = "/people";
    private static final String SERVICE_NAME = "people";

    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleRestService.class);

    @PostConstruct
    public void init() throws Exception {

//        DynamicPropertyFactory configInstance = DynamicPropertyFactory.getInstance();
//        ApplicationInfoManager applicationInfoManager = ApplicationInfoManager.getInstance();
//
//        DiscoveryManager.getInstance().initComponent(
//                new MyDataCenterInstanceConfig(),
//                new DefaultEurekaClientConfig());
//
//        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<Person> getPeople(@QueryParam("page") @DefaultValue("1") final int page, @Context HttpServletRequest req) {
        LOGGER.info("!!!!!!!!!!!!!! Start retrieving person's data.");

        return Lists.newArrayList(
                new Person().setFirstName("John").setLastName("Bombadil"),
                new Person().setFirstName("Jim").setLastName("Tommyknockers")
        );
    }

    @POST
    @Path("/process")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<Person> processIra(List<Person> persons) {
        LOGGER.info("!!!!!!!!!!!!!! Start retrieving person's data.");

        persons.get(0).setFirstName("Ira");
        persons.get(0).setLastName("Ptichka");

        return persons;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/stop")
    public String stop1() {
        LOGGER.info("!!!!!!!!!!!!!! Stopping service discovery");

        EurekaClient eurekaClient = DiscoveryManager.getInstance().getEurekaClient();

        if (eurekaClient != null) {
            eurekaClient.shutdown();
        }

        return "Stopped";
    }

}
