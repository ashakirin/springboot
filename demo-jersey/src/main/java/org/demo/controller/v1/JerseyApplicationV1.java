package org.demo.controller.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest/v1")
public class JerseyApplicationV1 extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(HelloJerseyV1.class);
        return classes;
    }
}
