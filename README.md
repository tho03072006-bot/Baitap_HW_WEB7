# Baitap_HW_WEB7 - CRUD API Category + Swagger 3 + AJAX RESTful API

Mon Lap trinh Web (WEBPR330479) - HCMUTE. Spring Boot 3.1.5, package `vn.iotstar`, JSP + AJAX (jQuery).

## Noi dung da lam

- **CRUD API Category**: `entity/Category.java`, `repository/CategoryRepository.java`, `service/ICategoryService.java` + Impl, `controller/api/CategoryApiController.java`.
- **CRUD API Product**: tuong tu Category, trong `controller/api/ProductApiController.java`.
- **Swagger 3**: `springdoc-openapi-starter-webmvc-ui` (khong dung `springfox-swagger-ui` vi chua tuong thich Jakarta EE/Spring Boot 3).
- **AJAX CRUD**: `WEB-INF/views/category.jsp`, `product.jsp` goi thang REST API bang jQuery.

Bang du lieu dat ten `HW7Categories` / `HW7Products` (thay vi `Categories`/`Products`) de khong trung voi bang `categories` da co san trong cung DB `webst2`.

## Chay project (Eclipse STS)

1. File > Import > Maven > Existing Maven Projects > chon thu muc nay.
2. File `application-secrets.properties` (chua mat khau SQL Server that) da co san, **khong commit len Git** (da o trong `.gitignore`).
3. Neu Hibernate (`ddl-auto=update`) khong tu tao duoc bang, chay tay script `database/hw7_tables.sql` trong SSMS.
4. Chay `Hw7Application.java`, mo trinh duyet:
   - http://localhost:8082/ - trang chu
   - http://localhost:8082/categories - CRUD Category (AJAX)
   - http://localhost:8082/products - CRUD Product (AJAX)
   - http://localhost:8082/swagger-ui/index.html - Swagger UI

## Nop bai (muc 6)

Da `git init` + commit cuc bo. Tao repo GitHub moi ten `Baitap_HW_WEB7` qua GitHub Desktop > Publish repository, roi nop link vao UTEXLMS.
