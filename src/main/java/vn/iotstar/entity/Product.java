package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entity Product - anh xa bang "HW7Products"
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

	// Ten file anh da luu tren server (vd: p3.png)
	@Column(length = 200)
	private String images;

	@Column(columnDefinition = "nvarchar(500) not null")
	private String description;

	@Column(nullable = false)
	private double discount;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date createDate;

	// 1 = dang ban, 0 = ngung ban
	private short status;

	// Khong @JsonIgnore de JSON tra ve kem ten Category cho product.jsp hien thi truc tiep
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
}
