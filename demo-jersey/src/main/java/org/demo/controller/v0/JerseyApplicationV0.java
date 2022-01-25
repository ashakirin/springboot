package org.demo.controller.v0;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest/v0")
public class JerseyApplicationV0 extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(HelloJerseyV0.class);
        return classes;
    }
}
