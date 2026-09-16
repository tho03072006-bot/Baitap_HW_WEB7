<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Quan ly Product (AJAX)</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>var contextPath = "${pageContext.request.contextPath}";</script>
</head>
<body>
	<div class="container mt-4 mb-5">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h3>Quan ly Product (Ajax + RESTful API)</h3>
			<a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary btn-sm">&laquo; Trang chu</a>
		</div>

		<p>
			<button class="btn btn-success ml-auto" onclick="showCreateNewProductModal()">
				<i class="fas fa-plus mr-2"></i>Them Product
			</button>
		</p>

		<table class="table table-striped table-bordered table-responsive">
			<thead class="table-dark">
				<tr>
					<th>Id</th>
					<th>Hinh</th>
					<th>Ten san pham</th>
					<th>Danh muc</th>
					<th>So luong</th>
					<th>Don gia</th>
					<th>Giam gia</th>
					<th>Trang thai</th>
					<th>Thao tac</th>
				</tr>
			</thead>
			<tbody id="productTableBody"></tbody>
		</table>
	</div>

	<!-- Modal them/sua Product dung chung -->
	<div class="modal" tabindex="-1" role="dialog" id="productModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="productForm" method="post" onsubmit="return false;" enctype="multipart/form-data">
					<div class="modal-header">
						<h5 class="modal-title" id="productModalTitle">Them Product</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>
					<div class="modal-body">
						<input type="hidden" id="productId" name="productId">
						<div class="form-group mb-2">
							<label for="productName">Ten san pham</label>
							<input type="text" class="form-control" id="productName" name="productName" required>
						</div>
						<div class="form-group mb-2">
							<label for="categoryId">Danh muc</label>
							<select class="form-control" id="categoryId" name="categoryId" required></select>
						</div>
						<div class="form-group mb-2">
							<label for="quantity">So luong</label>
							<input type="number" class="form-control" id="quantity" name="quantity" min="0" required>
						</div>
						<div class="form-group mb-2">
							<label for="unitPrice">Don gia</label>
							<input type="number" step="0.01" class="form-control" id="unitPrice" name="unitPrice" min="0" required>
						</div>
						<div class="form-group mb-2">
							<label for="discount">Giam gia</label>
							<input type="number" step="0.01" class="form-control" id="discount" name="discount" min="0" value="0" required>
						</div>
						<div class="form-group mb-2">
							<label for="description">Mo ta</label>
							<textarea class="form-control" id="description" name="description" rows="3" required></textarea>
						</div>
						<div class="form-group mb-2">
							<label for="status">Trang thai</label>
							<select class="form-control" id="status" name="status">
								<option value="1">Dang ban</option>
								<option value="0">Ngung ban</option>
							</select>
						</div>
						<div class="form-group mb-2">
							<label for="imageFile">Hinh anh (chon file moi neu muon doi)</label>
							<input type="file" class="form-control" id="imageFile" name="imageFile">
						</div>
						<div class="form-group row">
							<div class="col text-center">
								<button type="submit" class="btn btn-primary btn-block">Luu</button>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Dong</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript">
		var categoryList = [];

		/* Nap danh sach Category de do vao select */
		function loadCategoryOptions(selectedId) {
			$.getJSON(contextPath + '/api/category', function(res) {
				categoryList = res.body;
				var opt = [];
				for (var i = 0; i < categoryList.length; i++) {
					opt.push('<option value="' + categoryList[i].categoryId + '">' + categoryList[i].categoryName + '</option>');
				}
				$('#categoryId').html(opt.join(''));
				if (selectedId) {
					$('#categoryId').val(selectedId);
				}
			});
		}

		function categoryName(categoryId) {
			for (var i = 0; i < categoryList.length; i++) {
				if (categoryList[i].categoryId == categoryId) {
					return categoryList[i].categoryName;
				}
			}
			return '';
		}

		/* Get product - hien thi danh sach */
		function loadProducts() {
			$.getJSON(contextPath + '/api/product', function(res) {
				var json = res.body;
				var tr = [];
				for (var i = 0; i < json.length; i++) {
					var p = json[i];
					tr.push('<tr>');
					tr.push('<td>' + p.productId + '</td>');
					tr.push('<td>' + (p.images ? '<img src="' + contextPath + '/admin/products/images/' + p.images + '" style="width:70px" class="img-fluid" alt="">' : '') + '</td>');
					tr.push('<td>' + p.productName + '</td>');
					tr.push('<td>' + (p.category ? p.category.categoryName : categoryName(p.categoryId)) + '</td>');
					tr.push('<td>' + p.quantity + '</td>');
					tr.push('<td>' + p.unitPrice + '</td>');
					tr.push('<td>' + p.discount + '</td>');
					tr.push('<td>' + (p.status == 1 ? 'Dang ban' : 'Ngung ban') + '</td>');
					tr.push('<td>'
						+ '<a href="#" data-id="' + p.productId + '" class="btn btn-outline-warning btn-sm editproduct"><i class="fa fa-edit"></i></a> '
						+ '<a href="#" data-id="' + p.productId + '" class="btn btn-outline-danger btn-sm deleteproduct"><i class="fa fa-trash"></i></a>'
						+ '</td>');
					tr.push('</tr>');
				}
				$('#productTableBody').html(tr.join(''));
			});
		}

		$(document).ready(function() {
			loadCategoryOptions();
			loadProducts();
		});

		function showCreateNewProductModal() {
			$('#productForm')[0].reset();
			$('#productId').val('');
			$('#productModalTitle').text('Them Product');
			loadCategoryOptions();
			$('#productModal').modal('show');
		}

		/* Sua product: bam nut edit -> goi API lay chi tiet -> do vao form */
		$(document).delegate('.editproduct', 'click', function() {
			var id = $(this).data('id');
			$.post(contextPath + '/api/product/getProduct', { id: id }, function(res) {
				var p = res.body;
				$('#productForm')[0].reset();
				$('#productId').val(p.productId);
				$('#productName').val(p.productName);
				$('#quantity').val(p.quantity);
				$('#unitPrice').val(p.unitPrice);
				$('#discount').val(p.discount);
				$('#description').val(p.description);
				$('#status').val(p.status);
				loadCategoryOptions(p.category ? p.category.categoryId : p.categoryId);
				$('#productModalTitle').text('Cap nhat Product');
				$('#productModal').modal('show');
			}, 'json');
		});

		/* Them / Cap nhat product (dung chung 1 form) */
		$("form#productForm").submit(function(e) {
			e.preventDefault();
			var id = $('#productId').val();
			var formData = new FormData(this);
			var url = contextPath + '/api/product/addProduct';
			var type = 'POST';
			if (id) {
				url = contextPath + '/api/product/updateProduct';
				type = 'PUT';
			}

			$.ajax({
				url: url,
				type: type,
				dataType: "json",
				data: formData,
				success: function(data) {
					$('#productModal').modal('hide');
					loadProducts();
				},
				error: function(xhr) {
					alert(xhr.responseJSON ? xhr.responseJSON.message : "Co loi xay ra");
				},
				cache: false,
				contentType: false,
				processData: false
			});
		});

		/* Xoa product */
		$(document).delegate('.deleteproduct', 'click', function() {
			var id = $(this).data('id');
			if (confirm('Ban co chac muon xoa Product nay?')) {
				var row = $(this).closest('tr');
				$.ajax({
					type: "DELETE",
					url: contextPath + '/api/product/deleteProduct?productId=' + id,
					dataType: "json",
					success: function() {
						row.fadeOut('slow', function() {
							$(this).remove();
						});
					},
					error: function(xhr) {
						alert(xhr.responseJSON ? xhr.responseJSON.message : "Co loi xay ra");
					}
				});
			}
		});
	</script>
</body>
</html>
