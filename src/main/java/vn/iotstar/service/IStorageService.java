package vn.iotstar.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

	void init();

	void delete(String storeFilename) throws Exception;

	Path load(String filename);

	Resource loadAsResource(String filename);

	void store(MultipartFile file, String storeFilename);

	// Sinh ten file luu tren server tu file goc + id doi tuong. Vd: file "abc.png", id "3" -> "p3.png"
	String getSorageFilename(MultipartFile file, String id);
}
