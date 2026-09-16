package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity Product - anh xa toi bang "HW7Products" (doi ten tuong tu Category,
 * xem giai thich trong Category.java).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HW7Products")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(length = 500, columnDefinition = "nvarchar(500) not null")
	private String productName;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private double unitPrice;

	// Ten file anh da luu tren server (vd: p3.png), KHONG phai duong dan day du
	@Column(length = 200)
	private String images;

	@Column(columnDefinition = "nvarchar(500) not null")
	private String description;

	@Column(nullable = false)
	private double discount;

	@Temporal(TemporalType.TIMESTAMP)
	// LUU Ý: file huong dan goc ghi pattern = "YYYY-MM-DD hh:mi:ss" (cu phap SQL),
	// da sua lai dung cu phap Java (SimpleDateFormat): "yyyy-MM-dd HH:mm:ss",
	// neu khong se bi loi/parse sai khi format ngay gio.
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date createDate;

	// 1 = dang ban, 0 = ngung ban
	private short status;

	// LUU Y: file huong dan goc co dat @JsonIgnore o day, nhung bo di de JSON tra ve
	// cho trang product.jsp co the hien thi truc tiep ten Category (p.category.categoryName)
	// ma khong can goi them API. Khong bi vong lap vo han khi serialize JSON vi ben
	// Category.products (xem Category.java) da duoc @JsonIgnore roi.
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
}
