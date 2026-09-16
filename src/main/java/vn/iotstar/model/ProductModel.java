package vn.iotstar.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model trung gian nhan du lieu tu form (multipart/form-data) cua trang them/sua Product,
 * truoc khi chuyen thanh Entity Product de luu xuong database.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
	private Long productId;
	private String productName;
	private MultipartFile imageFile;
	private String images;
	private Double unitPrice;
	private Double discount;
	private String description;
	private Long categoryId;
	private Integer quantity;
	private Short status;
}
