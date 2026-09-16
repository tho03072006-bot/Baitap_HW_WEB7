<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Quan ly Category (AJAX)</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css">
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>var contextPath = "${pageContext.request.contextPath}";</script>
</head>
<body>
	<div class="container mt-4 mb-5">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h3>Quan ly Category (Ajax + RESTful API)</h3>
			<a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary btn-sm">&laquo; Trang chu</a>
		</div>

		<p>
			<button class="btn btn-success ml-auto" onclick="showCreateNewCategoryModal()">
				<i class="fas fa-plus mr-2"></i>Them Category
			</button>
		</p>

		<table class="table table-striped table-bordered table-responsive">
			<thead class="table-dark">
				<tr>
					<th>Id</th>
					<th>Icon</th>
					<th>Ten Category</th>
					<th>Thao tac</th>
				</tr>
			</thead>
			<tbody id="categoryTableBody"></tbody>
		</table>
	</div>

	<!-- Modal them Category -->
	<div class="modal" tabindex="-1" role="dialog" id="createCategoryModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="addCategory" method="post" onsubmit="return false;" enctype="multipart/form-data">
					<div class="modal-header">
						<h5 class="modal-title">Them Category</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
					</div>
					<div class="modal-body">
						<div class="form-group mb-2">
							<label for="new_categoryname">Ten Category</label>
							<input type="text" class="form-control" id="new_categoryname" name="categoryName" required>
						</div>
						<div class="form-group mb-2">
							<label for="new_icon">Icon</label>
							<input type="file" class="form-control" id="new_icon" name="icon">
						</div>
						<div class="form-group row">
							<div class="col text-center">
								<button type="submit" class="btn btn-primary btn-block">Them</button>
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

	<!-- Modal cap nhat Category -->
	<div class="modal" tabindex="-1" role="dialog" id="updateCategoryInfoModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Cap nhat Category</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="card mt-2">
						<div class="card-body pb-0">
							<form id="updateCategory" method="post" onsubmit="return false;" enctype="multipart/form-data">
								<div class="form-group mb-2">
									<label for="categoryName_up">Ten Category</label>
									<input type="text" class="form-control" id="categoryName_up" name="categoryName" required>
								</div>
								<div class="form-group mb-2">
									<label for="icon_up">Icon (chon file moi neu muon doi)</label>
									<input type="file" class="form-control" id="icon_up" name="icon">
								</div>
								<input type="hidden" id="categoryId_up" name="categoryId">
								<div class="form-group row">
									<div class="col text-center">
										<button type="submit" class="btn btn-primary btn-block">Cap nhat</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Dong</button>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript">
		/* Get category - hien thi danh sach */
		function loadCategories() {
			$.getJSON(contextPath + '/api/category', function(res) {
				var json = res.body;
				var tr = [];
				for (var i = 0; i < json.length; i++) {
					tr.push('<tr>');
					tr.push('<td>' + json[i].categoryId + '</td>');
					tr.push('<td>' + (json[i].icon ? '<img src="' + contextPath + '/admin/categories/images/' + json[i].icon + '" style="width:70px" class="img-fluid" alt="">' : '') + '</td>');
					tr.push('<td>' + json[i].categoryName + '</td>');
					tr.push('<td>'
						+ '<a href="#" data-id="' + json[i].categoryId + '" data-name="' + json[i].categoryName + '" class="btn btn-outline-warning btn-sm editcate"><i class="fa fa-edit"></i></a> '
						+ '<a href="#" data-id="' + json[i].categoryId + '" class="btn btn-outline-danger btn-sm deletecate"><i class="fa fa-trash"></i></a>'
						+ '</td>');
					tr.push('</tr>');
				}
				$('#categoryTableBody').html(tr.join(''));
			});
		}
		$(document).ready(function() {
			loadCategories();
		});

		/* Them category */
		$("form#addCategory").submit(function(e) {
			e.preventDefault();
			var formData = new FormData(this);

			$.ajax({
				url: contextPath + '/api/category/addCategory',
				type: 'POST',
				dataType: "json",
				data: formData,
				success: function(data) {
					$('#createCategoryModal').modal('hide');
					loadCategories();
				},
				error: function(xhr) {
					alert(xhr.responseJSON ? xhr.responseJSON.message : "Co loi xay ra");
				},
				cache: false,
				contentType: false,
				processData: false
			});
		});

		function showCreateNewCategoryModal() {
			$('#addCategory')[0].reset();
			$('#createCategoryModal').modal('show');
		}

		/* Sua category: bam nut edit -> mo modal + do du lieu vao form */
		$(document).delegate('.editcate', 'click', function() {
			var id = $(this).data('id');
			var name = $(this).data('name');
			$('#categoryName_up').val(name);
			$('#categoryId_up').val(id);
			$('#icon_up').val('');
			$('#updateCategoryInfoModal').modal('show');
		});

		$("form#updateCategory").submit(function(e) {
			e.preventDefault();
			var formData = new FormData(this);

			$.ajax({
				url: contextPath + '/api/category/updateCategory',
				type: 'PUT',
				dataType: "json",
				data: formData,
				success: function(data) {
					$('#updateCategoryInfoModal').modal('hide');
					loadCategories();
				},
				error: function(xhr) {
					alert(xhr.responseJSON ? xhr.responseJSON.message : "Co loi xay ra");
				},
				cache: false,
				contentType: false,
				processData: false
			});
		});

		/* Xoa category */
		$(document).delegate('.deletecate', 'click', function() {
			var id = $(this).data('id');
			if (confirm('Ban co chac muon xoa Category nay?')) {
				var row = $(this).closest('tr');
				$.ajax({
					type: "DELETE",
					url: contextPath + '/api/category/deleteCategory?categoryId=' + id,
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
