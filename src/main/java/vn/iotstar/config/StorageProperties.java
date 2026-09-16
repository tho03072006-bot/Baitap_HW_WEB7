package vn.iotstar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Doc gia tri "storage.location" trong application.properties.
 * Vd: storage.location=uploads
 */
@Data
@ConfigurationProperties("storage")
public class StorageProperties {
	private String location = "uploads";
}
