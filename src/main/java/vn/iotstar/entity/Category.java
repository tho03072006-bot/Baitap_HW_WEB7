package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entity Category - anh xa bang "HW7Categories" (doi ten de tranh trung voi bang "categories" da co san trong DB webst2)
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

	// 1 Category co nhieu Product. @JsonIgnore tranh vong lap khi chuyen sang JSON.
	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> products;
}
