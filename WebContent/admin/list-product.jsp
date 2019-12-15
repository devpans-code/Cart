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
    <title>List product | Admin</title>
    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css">
</head>
<style>
	html, body {
  		height: 100%;
	}
</style>
<body>
	<%@ page import="com.cartapp.model.User" %>
   	<% User userData = (User)session.getAttribute("user");  if(userData != null) { %>
	<div class="container">
		<% String msg = (String)request.getAttribute("message");
   			if(msg != null) { out.println(msg);}
		%>
		<div class="d-flex mt-4 mb-4">
			<div class="col-sm-6 d-flex">
				<a href="product" title="Product" class="mr-3">Product</a>
				<a href="customer" title="Customer">Customer</a>
			</div>
			<div class="col-sm-6 d-flex">
				<p style="margin-bottom: 0; line-height: 2.5">Welcom, <%=userData.getName() %></p>
				<a href="logout" class="btn btn-danger ml-3" title="">Logout</a>
			</div>	
		</div>
		<a href="product?action=add" class="btn btn-info mt-3 mb-3">Add product</a>
		<table class="table table-bordered" id="list_product">
			<thead>
				<tr>
					<th>Id</th>
					<th>Image</th>
					<th>Name</th>
					<th width="45%">Description</th>
					<th>Price</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
			 <%  Object[] products = (Object[]) request.getAttribute("productList");
			    	if(products.length > 0) {
					for (int i = 0; i < products.length; i++) {
						Product product = (Product)products[i]; 
				 %> 
					<tr>
						<td><%=product.getId()%></td>
						<td><img src="<%=request.getContextPath()%>/upload/<%=product.getImage()%>" width="50" height="50"></td>
						<td><%=product.getName()%></td>
						<td><%=product.getDescription()%></td>
						<td>$<%=(double)product.getPrice()%></td>
						<td>
							<a class="badge <%= product.getStatus().equals("Active") ? "badge-success" : "badge-danger" %>"><%= product.getStatus()%></a>
							<a href="product?action=change&id=<%=product.getId()%>" style="font-size: 12px;">Change status</a>
						</td>
						<td><a href="product?action=edit&id=<%=product.getId()%>">Edit</a> &nbsp; <a href="product?action=delete&id=<%=product.getId()%>">Delete</a></td>
					</tr>
				<%  } } %> 
			</tbody>
		</table>
	</div>
	<% } %>
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    /* $('#list_product').DataTable(); */
    
	$('#list_product').DataTable( {
        dom: 'Bfrtip',
        buttons: [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ]
    } );
    
	var data = $('#list_product').DataTable().buttons.exportData();
    
} );
</script>
</html>
