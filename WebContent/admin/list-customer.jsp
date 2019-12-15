<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.cartapp.model.User" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>List customer | Admin</title>
    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" rel="stylesheet">
</head>
<style>
	html, body {
  		height: 100%;
	}
</style>
<body>
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
				<p style="margin-bottom: 0; line-height: 2.5">Admin</p>
				<a href="logout" class="btn btn-danger ml-3" title="">Logout</a>
			</div>	
		</div>
		<table class="table table-bordered" id="list_user">
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Email</th>
					<th>Type</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
			 <%  Object[] users = (Object[]) request.getAttribute("userList");
			    	if(users.length > 0) {
					for (int i = 0; i < users.length; i++) {
						User u = (User)users[i]; 
				 %> 
					<tr>
						<td><%=u.getId()%></td>
						<td><%=u.getName()%></td>
						<td><%=u.getEmail()%></td>
						<td><%=u.getType()%></td>
						<td>
							<a class="badge <%= u.getStatus().equals("Active") ? "badge-success" : "badge-danger" %>"><%=u.getStatus()%></a>
							<a href="customer?action=change&id=<%=u.getId()%>" style="font-size: 12px;">Change status</a>
						</td>
					</tr>
				<%  } } %> 
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $('#list_user').DataTable();
} );
</script>
</html>
