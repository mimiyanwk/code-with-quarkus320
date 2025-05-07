package org.acme.getting.started;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.*;

@QuarkusIntegrationTest
@QuarkusTestResource(value = MongoTestResource.class, restrictToAnnotatedClass = true, parallel = true)
public class GreetingServiceIT {

    private static final Logger logger = Logger.getLogger(GreetingServiceIT.class);

    @Test
    void testGreetingHelloWorld() {
        logger.info("start calling testGreetingHelloWorld");
        try {
            Thread.sleep(10000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        logger.info("stop calling testGreetingHelloWorld");
    }
}
