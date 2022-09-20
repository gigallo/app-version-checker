package it.dedagroup.microservices.trec.rest;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.dedagroup.microservices.trec.appversionchecker.db.dao.AppVersionDao;
import it.dedagroup.microservices.trec.appversionchecker.enums.Platform;
import it.dedagroup.microservices.trec.rest.requests.AppVersionRequest;

@Path("/api/appversion")
public class AppVersionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppVersionController.class);
	
	@Inject
	AppVersionDao avDao;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getAppVersion(@QueryParam("platform") Platform platform) {
		
		LOGGER.info("Richiesta versione app per "+platform);
		String appVersion = avDao.getMaxByPlatform(platform);
		LOGGER.info("Versione sullo store: "+appVersion);
		
		return appVersion;
		
	}
	
	@POST
	@Path("/admin")
	public Response setAppVersion(AppVersionRequest request) {
		
		LOGGER.info("Inserimento nuova versione "+request.getVersion()+" per "+request.getPlatform());
		try {
			
			avDao.insertNewVersionForPlatform(request.getPlatform(), request.getVersion());
			LOGGER.info("Inserimento completato");
			return Response.status(Status.CREATED).build();
			
		} catch(Exception e) {
			
			LOGGER.error("Inserimento in errore: "+e.getMessage());
			return Response.status(400, e.getMessage()).build();
			
		}
		
	}
	
	@DELETE
	@Path("/admin")
	public Response deleteAppVersion(AppVersionRequest request) {
		
		LOGGER.info("Cancellazione versione "+request.getVersion()+" per "+request.getPlatform());
		long deleted = avDao.deleteVersionForPlatform(request.getPlatform(), request.getVersion());
		LOGGER.info("Numero di elementi cancellati: "+deleted);
		return Response.status(Status.NO_CONTENT).build();
		
	}

}
