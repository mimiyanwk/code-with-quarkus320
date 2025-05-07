package org.acme.getting.started;

import com.github.dockerjava.api.model.ContainerNetwork;
import io.quarkus.test.common.DevServicesContext;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.jboss.logging.Logger;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MongoTestResource implements QuarkusTestResourceLifecycleManager, DevServicesContext.ContextAware {

    public static final int MONGO_PORT = 27017;

    private static final Logger logger = Logger.getLogger(MongoTestResource.class);

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.9"));

    private static String connectStr;

    private Optional<String> containerNetworkId;

    @Override
    public Map<String, String> start() {
        // apply the network to the container
        containerNetworkId.ifPresent(mongoDBContainer::withNetworkMode);

        mongoDBContainer.start();

        // Replace hostname + port in the provided connection string with the hostname of the Docker container
        // running mongodb and the listening port.
        connectStr = mongoDBContainer.getConnectionString();
        logger.info("connect string: " + connectStr);

        String connectStrEnv = connectStr;
        if (containerNetworkId.isPresent()) {
            // Part of the connection string to replace
            String hostPort = String.format("%s:%d", mongoDBContainer.getHost(), mongoDBContainer.getMappedPort(MONGO_PORT));

            // Host/IP on the container network plus the unmapped port
            ContainerNetwork cn = (ContainerNetwork)mongoDBContainer.getCurrentContainerInfo().getNetworkSettings().getNetworks().values().toArray()[0];
            String networkHostPort = String.format("%s:%d", cn.getIpAddress(), MONGO_PORT);

            connectStrEnv = connectStrEnv.replace(hostPort, networkHostPort);
        }

        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.mongodb.connection-string", connectStrEnv);
        return properties;
    }

    @Override
    public void stop() {
        if (mongoDBContainer != null) {
            mongoDBContainer.stop();
        }
        System.clearProperty("quarkus.mongodb.connection-string");
    }

    public static String getConnectionString() {
        return connectStr;
    }

    @Override
    public void setIntegrationTestContext(DevServicesContext context) {
        containerNetworkId = context.containerNetworkId();
    }
}
