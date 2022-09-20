package it.dedagroup.microservices.trec.appversionchecker.db.dao;

import javax.inject.Singleton;
import javax.transaction.Transactional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import it.dedagroup.microservices.trec.appversionchecker.db.entities.AppVersion;
import it.dedagroup.microservices.trec.appversionchecker.enums.Platform;

@Singleton
public class AppVersionDao implements PanacheRepository<AppVersion> {
	
	public String getMaxByPlatform(Platform platform) {
		
		AppVersion av = find("platform = :platform", Sort.descending("id"), Parameters.with("platform", platform.toString()))
				.firstResult();
		if(av != null) {
			return av.getStoreVersion();
		}
		return "-1";
		
	}
	
	public long countByPlatformAndVersion(Platform platform, String version) {
		
		Parameters params = new Parameters();
		params.and("platform", platform.toString());
		params.and("storeVersion", version);
		
		return find("platform = :platform and storeVersion = :storeVersion", params).count();
		
	}
	
	@Transactional
	public void insertNewVersionForPlatform(Platform platform, String version) throws Exception {
		
		if(version.contains("v")) {
			version = version.replace("v", "");
		}
		
		if(countByPlatformAndVersion(platform, version) == 1) {
			throw new Exception("Versione già presente per questa piattaforma");
		}
		
		AppVersion av = new AppVersion();
		av.setPlatform(platform.toString());
		av.setStoreVersion(version);
		
		persist(av);
		
	}
	
	@Transactional
	public long deleteVersionForPlatform(Platform platform, String version) {
		
		Parameters params = new Parameters();
		params.and("platform", platform.toString());
		params.and("storeVersion", version);
		
		return delete("platform = :platform and storeVersion = :storeVersion", params);
		
	}

}
