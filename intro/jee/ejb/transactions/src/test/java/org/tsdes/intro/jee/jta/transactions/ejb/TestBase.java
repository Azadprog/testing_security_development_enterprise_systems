package org.tsdes.intro.jee.jta.transactions.ejb;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestBase {

    protected static EJBContainer ec;
    protected static Context ctx;

    protected static QueriesEJB queriesEJB;

    @BeforeAll
    public static void initContainer() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File("target/classes"));
        ec = EJBContainer.createEJBContainer(properties);
        ctx = ec.getContext();

        queriesEJB = getEJB(QueriesEJB.class);
    }

    protected static <T> T getEJB(Class<T> klass){
        try {
            return (T) ctx.lookup("java:global/classes/"+klass.getSimpleName()+"!"+klass.getName());
        } catch (NamingException e) {
            return null;
        }
    }

    @AfterAll
    public static void closeContainer() throws Exception {
        if (ctx != null)
            ctx.close();
        if (ec != null)
            ec.close();
    }

    @BeforeEach
    @AfterEach
    public void emptyDatabase(){
        //this is quicker than re-initialize the whole DB / EJB container
        queriesEJB.deleteAll();
    }

}
