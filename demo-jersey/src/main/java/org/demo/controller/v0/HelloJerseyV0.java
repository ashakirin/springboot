package org.demo.controller.v0;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@Produces("application/json")
public class HelloJerseyV0 {
    @GET
    @Path("/hello")
    public String hello() {
        return "{\"prop\": \"hello v0\"}";
    }
}
