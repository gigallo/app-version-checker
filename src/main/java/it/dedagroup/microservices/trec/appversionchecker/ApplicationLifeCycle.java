package it.dedagroup.microservices.trec.appversionchecker;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;

@ApplicationScoped
public class ApplicationLifeCycle {

  Logger logger = LoggerFactory.getLogger(ApplicationLifeCycle.class);

  void onStart(@Observes StartupEvent ev) {
    logger.info("The application is starting with profile " + ProfileManager.getActiveProfile());
  }
}