# Baitap_HW_WEB7 - CRUD API Category + Swagger 3 + AJAX RESTful API

Bai tap ve nha (Phan 2), mon Lap trinh Web (WEBPR330479) - HCMUTE, GVHD ThS. Nguyen Huu Trung.

## Doi chieu voi de bai

| # | Yeu cau | Da lam |
|---|---|---|
| 3 | CRUD API Category tren Spring Boot 3 (theo file "HUONG DAN CRUD API CATEGORY TREN SPRING BOOT 3") | `entity/Category.java`, `repository/CategoryRepository.java`, `service/ICategoryService.java` + `CategoryServiceImpl.java`, `controller/api/CategoryApiController.java` (GET/getCategory/addCategory/updateCategory/deleteCategory) |
| 4 | Cau hinh Swagger 3 (theo file "CAU HINH SWAGGER2 VA SWAGGER 3") | `springdoc-openapi-starter-webmvc-ui` trong `pom.xml`, xem tai `/swagger-ui/index.html` |
| 5 | Viet API + render AJAX cho CRUD tren bang Product va Category | `controller/api/ProductApiController.java` + `WEB-INF/views/category.jsp`, `WEB-INF/views/product.jsp` (jQuery AJAX goi thang API JSON) |
| 6 | Tao repo GitHub moi, commit muc 3,4,5, nop link | Xem huong dan ben duoi |

## Mot vai diem CHU DONG DIEU CHINH so voi file huong dan goc (co ghi chu trong code)

- Bo dependency `springfox-swagger-ui:3.0.0` ma file huong dan co liet ke: thu vien nay chua ho tro
  Jakarta EE nen se lam Spring Boot 3 loi ngay luc khoi dong. Chi giu `springdoc-openapi-starter-webmvc-ui`
  (dung chuan cho Spring Boot 3), van dung dung version 2.0.2 nhu file huong dan.
- Doi ten bang `Categories`/`Products` (trong file huong dan) thanh `HW7Categories`/`HW7Products`
  de KHONG bi trung ten voi bang `categories` da co san trong cung database `webst2` tu bai tap khac
  (SQL Server mac dinh so sanh ten bang khong phan biet hoa/thuong).
- Sua pattern `@DateTimeFormat` cua truong `createDate` (Product) tu `"YYYY-MM-DD hh:mi:ss"` (cu phap SQL)
  thanh `"yyyy-MM-dd HH:mm:ss"` (dung cu phap Java).
- Bo sung day du update/delete cho Product (file huong dan AJAX chi lam mau `addProduct`, ghi chu
  "Bai tap them: Xay dung chuc nang CRUD bang Products bang Ajax" - da lam day du theo dung yeu cau muc 5).

## Cau truc chinh

```
src/main/java/vn/iotstar/
  entity/            Category, Product
  repository/         CategoryRepository, ProductRepository
  service/            IStorageService (upload file) + IxxxService/xxxServiceImpl
  model/               Response, ProductModel
  config/              StorageProperties
  exception/           StorageException, StorageFileNotFoundException
  controller/api/      CategoryApiController, ProductApiController (REST JSON, /api/...)
  controller/          PageController (tra ve JSP), ImageController (tra ve file anh)
src/main/webapp/WEB-INF/views/
  index.jsp, category.jsp, product.jsp
database/hw7_tables.sql   script tao bang du phong neu Hibernate khong tu tao duoc
```

## Chay project (trong Eclipse STS)

1. File > Import > Maven > Existing Maven Projects > chon thu muc `Baitap_HW_WEB7` nay.
2. Copy file `application-secrets.properties.example` thanh `application-secrets.properties`
   (cung cap goc, ngang hang `pom.xml`), dien mat khau SQL Server that cua ban vao dong `db.password=`.
3. Neu chua co database `webst2` tren SQL Server (localhost,1433) thi tao truoc (giong cac bai tap
   truoc). Bang `HW7Categories`/`HW7Products` se tu duoc Hibernate tao khi chay lan dau
   (`spring.jpa.hibernate.ddl-auto=update`); neu khong tu tao duoc, chay tay script
   `database/hw7_tables.sql` trong SSMS.
4. Chay `Hw7Application.java` (Run As > Spring Boot App / Java Application).
5. Mo trinh duyet:
   - Trang chu: http://localhost:8082/
   - Trang quan ly Category (AJAX): http://localhost:8082/categories
   - Trang quan ly Product (AJAX): http://localhost:8082/products
   - Swagger UI: http://localhost:8082/swagger-ui/index.html

## Muc 6 - Tao repo GitHub va nop link

1. Mo GitHub Desktop (hoac `git`), tao repository moi ten `Baitap_HW_WEB7` (co the de Public hoac
   Private tuy y, GVHD chi can xem duoc link).
2. File `.gitignore` da san (bo qua `target/`, `uploads/`, va **dac biet la
   `application-secrets.properties`** de khong lo mat khau that len GitHub cong khai).
3. Add > Commit lan dau ("Initial commit - CRUD API Category, Swagger3, AJAX RESTful API") > Publish repository.
4. Copy link repo (vd `https://github.com/<username>/Baitap_HW_WEB7`) va nop vao UTEXLMS theo dung yeu cau muc 6.
