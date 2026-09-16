package vn.iotstar.controller.api;

import java.sql.Timestamp;
import java.util.Date;
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
import vn.iotstar.entity.Product;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IStorageService;

/**
 * REST API CRUD cho Product (bai tap them - Buoc 2 trong file huong dan AJAX voi RESTful API,
 * xay dung tuong tu CategoryApiController: addProduct, getProduct, updateProduct, deleteProduct).
 */
@RestController
@RequestMapping(path = "/api/product")
@Tag(name = "Product API", description = "CRUD cho san pham (Product)")
public class ProductApiController {

	@Autowired
	private IProductService productService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IStorageService storageService;

	@GetMapping
	public ResponseEntity<?> getAllProduct() {
		return new ResponseEntity<Response>(new Response(true, "Thanh cong", productService.findAll()), HttpStatus.OK);
	}

	@PostMapping(path = "/getProduct")
	public ResponseEntity<?> getProduct(@Validated @RequestParam("id") Long id) {
		Optional<Product> product = productService.findById(id);
		if (product.isPresent()) {
			return new ResponseEntity<Response>(new Response(true, "Thanh cong", product.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(new Response(false, "That bai", null), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/addProduct")
	public ResponseEntity<?> addProduct(
			@Validated @RequestParam("productName") String productName,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			@Validated @RequestParam("unitPrice") Double unitPrice,
			@Validated @RequestParam("discount") Double discount,
			@Validated @RequestParam("description") String description,
			@Validated @RequestParam("categoryId") Long categoryId,
			@Validated @RequestParam("quantity") Integer quantity,
			@Validated @RequestParam("status") Short status) {

		Optional<Product> optProduct = productService.findByProductName(productName);
		if (optProduct.isPresent()) {
			return new ResponseEntity<Response>(
					new Response(false, "San pham nay da ton tai trong he thong", null), HttpStatus.BAD_REQUEST);
		}

		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<Response>(
					new Response(false, "Khong tim thay Category", null), HttpStatus.BAD_REQUEST);
		}

		Product product = new Product();
		product.setProductName(productName);
		product.setUnitPrice(unitPrice);
		product.setDiscount(discount);
		product.setDescription(description);
		product.setQuantity(quantity);
		product.setStatus(status);
		product.setCategory(optCategory.get());
		product.setCreateDate(new Timestamp(new Date().getTime()));

		// kiem tra ton tai file, luu file
		if (imageFile != null && !imageFile.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			// luu file vao truong images
			product.setImages(storageService.getSorageFilename(imageFile, uuString));
			storageService.store(imageFile, product.getImages());
		}

		productService.save(product);
		return new ResponseEntity<Response>(new Response(true, "Them Thanh cong", product), HttpStatus.OK);
	}

	@PutMapping(path = "/updateProduct")
	public ResponseEntity<?> updateProduct(
			@Validated @RequestParam("productId") Long productId,
			@Validated @RequestParam("productName") String productName,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			@Validated @RequestParam("unitPrice") Double unitPrice,
			@Validated @RequestParam("discount") Double discount,
			@Validated @RequestParam("description") String description,
			@Validated @RequestParam("categoryId") Long categoryId,
			@Validated @RequestParam("quantity") Integer quantity,
			@Validated @RequestParam("status") Short status) {

		Optional<Product> optProduct = productService.findById(productId);
		if (optProduct.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Khong tim thay Product", null), HttpStatus.BAD_REQUEST);
		}

		Optional<Category> optCategory = categoryService.findById(categoryId);
		if (optCategory.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Khong tim thay Category", null), HttpStatus.BAD_REQUEST);
		}

		Product product = optProduct.get();
		product.setProductName(productName);
		product.setUnitPrice(unitPrice);
		product.setDiscount(discount);
		product.setDescription(description);
		product.setQuantity(quantity);
		product.setStatus(status);
		product.setCategory(optCategory.get());

		// kiem tra ton tai file, luu file
		if (imageFile != null && !imageFile.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			product.setImages(storageService.getSorageFilename(imageFile, uuString));
			storageService.store(imageFile, product.getImages());
		}

		productService.save(product);
		return new ResponseEntity<Response>(new Response(true, "Cap nhat Thanh cong", product), HttpStatus.OK);
	}

	@DeleteMapping(path = "/deleteProduct")
	public ResponseEntity<?> deleteProduct(@Validated @RequestParam("productId") Long productId) {
		Optional<Product> optProduct = productService.findById(productId);
		if (optProduct.isEmpty()) {
			return new ResponseEntity<Response>(new Response(false, "Khong tim thay Product", null), HttpStatus.BAD_REQUEST);
		} else {
			productService.delete(optProduct.get());
			return new ResponseEntity<Response>(new Response(true, "Xoa Thanh cong", optProduct.get()), HttpStatus.OK);
		}
	}
}
