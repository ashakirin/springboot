package org.demo.controller.v1;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@Produces("application/json")
public class HelloJerseyV1 {
    @GET
    @Path("/hello")
    public String hello() {
        return "{\"prop\": \"hello v1\"}";
    }
}
