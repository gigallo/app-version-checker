package it.dedagroup.microservices.trec.appversionchecker.tests;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.test.junit.QuarkusTest;
import it.dedagroup.microservices.trec.appversionchecker.db.dao.AppVersionDao;
import it.dedagroup.microservices.trec.appversionchecker.enums.Platform;

@QuarkusTest
public class DaoTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DaoTest.class);
	
	@Inject
	private AppVersionDao dao;

	@Test
	public void testGetLastVersion() {
		
		String version = dao.getMaxByPlatform(Platform.IOS);
		
		Assertions.assertEquals("1.0.1", version);
		
	}
	
	@Test
	public void testInsertNewVersion() {
		
		Assertions.assertThrows(Exception.class, () -> {dao.insertNewVersionForPlatform(Platform.IOS, "1.0.1"); });
		
		try {
			dao.insertNewVersionForPlatform(Platform.IOS, "100.0.2");
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			Assertions.fail();
		}
		
		String version = dao.getMaxByPlatform(Platform.IOS);
		Assertions.assertEquals("100.0.2", version);
		
		long deletedVersions = dao.deleteVersionForPlatform(Platform.IOS, "100.0.2");
		
		Assertions.assertEquals(1, deletedVersions);
		
		
	}

}
