package vn.iotstar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import vn.iotstar.config.StorageProperties;
import vn.iotstar.service.IStorageService;

/**
 * Class chay chinh cua ung dung Spring Boot.
 * - @EnableConfigurationProperties(StorageProperties.class): nap cau hinh "storage.location"
 * trong application.properties vao class StorageProperties (Buoc 6 - upload file).
 * - extends SpringBootServletInitializer: cho phep deploy thanh file WAR len servlet
 * container ben ngoai (Tomcat that) neu can, ben canh cach chay embedded binh thuong.
 */
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Hw7Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Hw7Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Hw7Application.class);
	}

	// Thu muc luu file upload (icon Category, hinh Product) duoc tao san khi app khoi dong
	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}
}
