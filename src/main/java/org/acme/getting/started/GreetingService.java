package org.acme.getting.started;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static io.quarkus.scheduler.Scheduled.ConcurrentExecution.SKIP;

@ApplicationScoped
public class GreetingService {

    @ConfigProperty(name = "db-database")
    String database;

    @Scheduled(every = "${print.info.every.expr:1s}", concurrentExecution = SKIP)
    void printInfo() {
        Log.infov("greeting service is running");
        Log.infov("database {0}", database);
    }
}
