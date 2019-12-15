<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.cartapp.model.Product" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Edit product | Admin</title>
    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<style>
	html, body {
  		height: 100%;
	}
	.is-required{
	color: #dc3545;}
</style>
<body>
	<div class="container">
		<% String msg = (String)request.getAttribute("message");
   			if(msg != null) { out.println(msg);}
		%>
		<div class="d-flex mt-4 mb-4">
			<div class="col-sm-6 d-flex">
				<a href="product" title="Product" class="mr-3">Product</a>
				<a href="list-customer.jsp" title="Customer">Customer</a>
			</div>
			<div class="col-sm-6 d-flex">
				<p style="margin-bottom: 0; line-height: 2.5">Admin</p>
				<a href="logout" class="btn btn-danger ml-3" title="">Logout</a>
			</div>	
		</div>
		<% Product product = (Product)request.getAttribute("productInfo"); %>
		<form class="mx-auto col-sm-6" id="edit_product" action="${pageContext.request.contextPath}/product" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="<%=product.getId() %>">
			<div class="form-group">
				<lable for="product_name">Name <span class="is-required">*</span></lable>
				<input type="text" name="name" placeholder="Enter name" value="<%=product.getName() %>" id="product_name" class="form-control" autofocus="autofocus">
			</div>
			<div class="form-group">
				<lable for="product_desc">Description <span class="is-required">*</span></lable>
				<textarea rows=3"" name="description" placeholder="Enter description" id="product_desc" class="form-control"><%=product.getDescription() %></textarea>
			</div>
			<div class="form-group">
				<lable for=""product_price"">Price <span class="is-required">*</span></lable>
				<input type="text" name="price" placeholder="Enter price" value="<%=product.getPrice() %>" id="product_price" class="form-control">
			</div>
			<div class="form-group">
				<lable for="product_name">Image</lable>
				<input type="file" name="image" id="product_image" class="form-control">
				<br>
				<img alt="oldImage" src="<%=request.getContextPath()%>/upload/<%=product.getImage()%>" width="100" height="100">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-success">Update product</button>
			</div>
		</form>
		
	</div>
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#edit_product').validate({
			errorElement: 'div',
			errorClass: 'help-block',
			rules:{
				name: { required: true },
				description: { required: true },
				price: { required: true },
				//image: { required: true }
			},
			messages:{
				name: {required: "Please, enter name"},
				description: {required: "Please, enter description"},
				price: { required: "Please, enter price"},
				//image: { required: "Please, choose image"}
			},
			highlight: function(e){
				$(e).addClass('has-error');
			},
			unhighlight: function(e){
				$(e).removeClass('has-error');
			},
			errorPlacement: function(e, element){
				e.insertAfter(element);
			},
			success: function(){
				$(this).submit();
			}
		});
	});
</script>
</html>
