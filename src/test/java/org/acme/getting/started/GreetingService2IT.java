package org.acme.getting.started;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
public class GreetingService2IT {
    private static final Logger logger = Logger.getLogger(GreetingService2IT.class);

    @Test
    void testGreetingHelloTestCase2() {
        logger.info("start calling testGreetingHelloTestCase2");
        try {
            Thread.sleep(10000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        logger.info("stop calling testGreetingHelloTestCase2");
    }
}
