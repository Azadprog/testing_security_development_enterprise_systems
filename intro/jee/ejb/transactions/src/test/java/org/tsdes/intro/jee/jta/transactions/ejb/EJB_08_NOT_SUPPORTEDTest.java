package org.tsdes.intro.jee.jta.transactions.ejb;


import org.junit.jupiter.api.Test;

import javax.ejb.EJBException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


public class EJB_08_NOT_SUPPORTEDTest extends TestBase{


    @Test
    public void testDirectCall(){
        EJB_08_NOT_SUPPORTED ejb = getEJB(EJB_08_NOT_SUPPORTED.class);

        String name = "abc";

        //write outside of a transaction will fail
        try {
            ejb.createFooNotSupported(name);
            fail();
        }catch (EJBException e){
            //expected
        }
    }

    @Test
    public void testIndirectCall(){
        EJB_08_NOT_SUPPORTED ejb = getEJB(EJB_08_NOT_SUPPORTED.class);

        String name = "abc";

        assertFalse(queriesEJB.isInDB(name));

        ejb.createFooIndirectly(name);

        assertTrue(queriesEJB.isInDB(name));
    }


    @Test
    public void testIndirectEJB(){
        EJB_08_NOT_SUPPORTED ejb = getEJB(EJB_08_NOT_SUPPORTED.class);

        String name = "abc";

        //even if this method will create a transaction, then its internal call will be
        //outside one due to NOT_SUPPORTED
        try {
            ejb.createFooIndirectlyWithEJBCall(name);
            fail();
        }catch (EJBException e){
            //expected
        }
    }


    @Test
    public void testIndirectSupports(){
        EJB_08_NOT_SUPPORTED ejb = getEJB(EJB_08_NOT_SUPPORTED.class);

        String name = "abc";

        assertFalse(queriesEJB.isInDB(name));

        //should be fine, as re-using the caller's transaction
        ejb.createFooIndirectlyWithEJBCallWithSupports(name);

        assertTrue(queriesEJB.isInDB(name));
    }


}