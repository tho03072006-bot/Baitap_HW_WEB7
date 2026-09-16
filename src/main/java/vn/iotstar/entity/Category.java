package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity Category - anh xa toi bang "HW7Categories".
 * LUU Y: file huong dan goc dat ten bang la "Categories", nhung o day doi thanh
 * "HW7Categories" de KHONG trung/dung do voi bang "categories" da co san trong
 * cung database tu bai tap truoc (SQL Server mac dinh so sanh ten bang KHONG phan
 * biet hoa/thuong nen "Categories" va "categories" se bi xem la MOT bang).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HW7Categories")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;

	@Column(length = 500, columnDefinition = "nvarchar(500) not null")
	private String categoryName;

	@Column(length = 200)
	private String icon;

	// 1 Category co nhieu Product. Dung @JsonIgnore de tranh vong lap vo han
	// khi Product -> Category -> Product -> ... luc chuyen sang JSON.
	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> products;
}
