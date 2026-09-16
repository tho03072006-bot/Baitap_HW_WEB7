package vn.iotstar.controller.api;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;
import vn.iotstar.entity.Category;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IStorageService;

/**
 * REST API CRUD cho Category (Buoc 7 trong file huong dan CRUD API Category tren Spring Boot 3).
 */
@RestController
@RequestMapping(path = "/api/category")
@Tag(name = "Category API", description = "CRUD cho danh muc san pham (Category)")
public class CategoryApiController {

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IStorageService storageService;

	@GetMapping
	public ResponseEntity<?> getAllCategory() {
		return new ResponseEntity<Response>(new Response(true, "Thanh cong", categoryService.findAll()), HttpStatus.OK);
	}

	@PostMapping(path = "/getCategory")
	public ResponseEntity<?> getCategory(@Validated @RequestParam("id") Long id) {
		Optional<Category> category = categoryService.findById(id);
		if (category.isPresent()) {
			return new ResponseEntity<Response>(new Response(true, "Thanh cong", category.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response(false, "That bai", null), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/addCategory")
	public ResponseEntity<?> addCategory(@Validated @RequestParam("categoryName") String categoryName,
			@RequestParam(value = "icon", required = false) MultipartFile icon) {
		Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);

		if (optCategory.isPresent()) {
			return new ResponseEntity<Response>(
					new Response(false, "Category da ton tai trong he thong", null), HttpStatus.BAD_REQUEST);
		} else {
			Category category = new Category();
			// kiem tra ton tai file, luu file
			if (icon != null && !icon.isEmpty()) {
				UUID uuid = UUID.randomUUID();
				String uuString = uuid.toString();
				// luu file vao truong icon
				category.setIcon(storageService.getSorageFilename(icon, uuString));
				storageService.store(icon, category.getIcon());
			}
			category.setCategoryName(categoryName);
			categoryService.save(category);
			return new ResponseEntity<Response>(new Response(true, "Them Thanh cong", category), HttpStatus.OK);
		}
	}

	@PutMapping(path = "/updateCategory")
	public ResponseEntity<?> updateCategory(@Validated @RequestParam("categoryId") Long categoryId,
			@Validated @RequestParam("categoryName") String categoryName,
			@RequestParam(value = "icon", required = false) MultipartFile icon) {
		Optional<Category> optCategory = categoryService.findById(categoryId);

		if (optCategory.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Khong tim thay Category", null), HttpStatus.BAD_REQUEST);
		} else {
			// kiem tra ton tai file, luu file
			if (icon != null && !icon.isEmpty()) {
				UUID uuid = UUID.randomUUID();
				String uuString = uuid.toString();
				// luu file vao truong icon
				optCategory.get().setIcon(storageService.getSorageFilename(icon, uuString));
				storageService.store(icon, optCategory.get().getIcon());
			}

			optCategory.get().setCategoryName(categoryName);
			categoryService.save(optCategory.get());
			return new ResponseEntity<Response>(new Response(true, "Cap nhat Thanh cong", optCategory.get()), HttpStatus.OK);
		}
	}

	@DeleteMapping(path = "/deleteCategory")
	public ResponseEntity<?> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Khong tim thay Category", null), HttpStatus.BAD_REQUEST);
		} else {
			categoryService.delete(optCategory.get());
			return new ResponseEntity<Response>(new Response(true, "Xoa Thanh cong", optCategory.get()), HttpStatus.OK);
		}
	}
}
