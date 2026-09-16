package vn.iotstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vn.iotstar.service.IStorageService;

/**
 * Tra ve file anh/icon da upload (icon cua Category, hinh cua Product) de JSP
 * hien thi qua the <img src="...">.
 */
@RestController
public class ImageController {

	@Autowired
	private IStorageService storageService;

	@GetMapping("/admin/categories/images/{filename:.+}")
	public ResponseEntity<?> getCategoryImage(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("/admin/products/images/{filename:.+}")
	public ResponseEntity<?> getProductImage(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
