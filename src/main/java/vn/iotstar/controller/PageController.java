package vn.iotstar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller phuc vu cac trang JSP (khac voi Controller API tra ve JSON).
 * Ten view duoc noi voi spring.mvc.view.prefix/suffix trong application.properties
 * (/WEB-INF/views/ ... .jsp).
 */
@Controller
public class PageController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/categories")
	public String categoryPage() {
		return "category";
	}

	@GetMapping("/products")
	public String productPage() {
		return "product";
	}
}
