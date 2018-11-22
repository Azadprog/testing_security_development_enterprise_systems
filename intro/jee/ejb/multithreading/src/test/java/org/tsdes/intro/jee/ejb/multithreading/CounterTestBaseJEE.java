package org.tsdes.intro.jee.ejb.multithreading;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class CounterTestBaseJEE extends CounterTestBase {

    private EJBContainer ec;
    private Context ctx;

    @BeforeEach
    public void initContainer() {

        /*
            Using an embedded JEE container...
            recall, this is done just to simplify those examples, but
            you will have to use Arquillian when testing an actual application
         */

        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File("target/classes"));
        ec = EJBContainer.createEJBContainer(properties);
        ctx = ec.getContext();
    }

    @AfterEach
    public void closeContainer() throws Exception {
        if (ctx != null)
            ctx.close();
        if (ec != null)
            ec.close();
    }

    @Override
    public Counter getCounter() {
        String name = getSingletonClass().getSimpleName();
        try {
            return (Counter) ctx.lookup("java:global/classes/" + name + "!" + Counter.class.getName());
        } catch (NamingException e) {
            return null;
        }
    }

    protected abstract Class<?> getSingletonClass();
}
