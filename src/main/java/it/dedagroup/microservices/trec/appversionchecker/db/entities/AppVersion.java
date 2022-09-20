package it.dedagroup.microservices.trec.appversionchecker.db.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import it.dedagroup.microservices.trec.appversionchecker.enums.Platform;

@Entity(name = "APP_VERSIONS")
public class AppVersion extends PanacheEntity {
	
	private Long id;
	
	private String platform;
	
	@Column(name = "store_version")
	private String storeVersion;
	
	@Column(name = "creation_date", columnDefinition = "datetime default current_timestamp()", insertable = false)
	private Date creationDate;
	
	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getStoreVersion() {
		return storeVersion;
	}

	public void setStoreVersion(String storeVersion) {
		this.storeVersion = storeVersion;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
