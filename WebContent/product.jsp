<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.cartapp.model.Product" %>
<%@ page import="com.cartapp.model.Order" %>
<%@ page import="com.cartapp.model.Cart" %>
<%@ page import="com.cartapp.model.CartItem" %>
<%@ page import="com.cartapp.model.SessionCounter" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Product list | Cart</title>
    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<style>
	:root {
  		--jumbotron-padding-y: 3rem;
	}

	.jumbotron {
  		padding-top: var(--jumbotron-padding-y);
  		padding-bottom: var(--jumbotron-padding-y);
  		margin-bottom: 0;
  		background-color: #fff;
	}
	@media (min-width: 768px) {
	  	.jumbotron {
	    	padding-top: calc(var(--jumbotron-padding-y) * 2);
	    	padding-bottom: calc(var(--jumbotron-padding-y) * 2);
	  	}
	}

	.jumbotron p:last-child {
  		margin-bottom: 0;
	}

	.jumbotron-heading {
  		font-weight: 300;
	}

	.jumbotron .container {
  		max-width: 40rem;
	}

	footer {
  		padding-top: 3rem;
  		padding-bottom: 3rem;
	}

	footer p {
  		margin-bottom: .25rem;		
	}
	.box-shadow { box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05); }
	
	.cart-icon{
	margin-left: 15px;}
	
	.cart-icon i{
	font-size: 26px;
	margin-top: 6px;}
	
	.badge-success{
	position: absolute;
    margin-left: 15px;
    top: 45px;}
    .img-cart{
    width: 65px;
    height: 65px;
    margin-right: 10px;
    margin-right: 5%;
    margin-top: 2px;}
    .img-cart img{
    width: 100%;}
    .minus, .plus{
    padding: 6px 10px;
    cursor: pointer;}
    .quantity{
    width: 50px; height: 31px; text-align: center;}
    .order-cart-list .cart-item:not(last-child){
    border-bottom: 1px solid #989898;}
    .order-cart-list .cart-item:not(first-child){
    margin-top: 10px;}
    .order-cart-list .cart-item:not(last-child){
    padding-bottom: 10px;}
    .card-image-div{
    	width: 100%;
    	padding: 25px 0px;
    	text-align: center;
    }
    .card-img-top-width{
    	width: 50%;
    }
    .table td, .table th {
    	font-size: 13px;
    	text-align: center;
    }
    .modal-dialog-new-width{
    width: 750px !important;}
    .modal-header{
        border-bottom: none !important;
        padding-bottom: 0px !important;
    	padding-top: 20px !important;
	}
</style>
<body>
	<%@ page import="com.cartapp.model.User" %>
	<% Cart cart = (Cart) session.getAttribute("cart"); %>
    <main role="main">
      	<div class="album py-5 bg-light">
        	<div class="container">
        		<div style="margin-bottom: 10px;">
   					<% String msg = (String)request.getAttribute("message");
  						if(msg != null) { out.println(msg);}
					%>
   				</div>
        		<div class="d-flex mb-4">
        			<%User userData = (User)session.getAttribute("user"); %>
					<p style="margin-bottom: 0; line-height: 2.5">Welcome, <%=(userData != null) ? userData.getName() : null %></p>
					<a href="" class="cart-icon" title="Cart" data-toggle="modal" data-target="#exampleModal">
						<span class="badge badge-success"><%= (cart != null) ? cart.getNumberOfItems() : 0%></span>
						<i class="fa fa-cart-plus"></i>
					</a>
					<a href="logout" class="btn btn-danger ml-3 mr-3" title="">Logout</a>
					<input type="text" name="search" placeholder="Search product..." onkeyup="search(this.value)">
				</div>
				<div> Current active users : <%=SessionCounter.getSessionCount() %></div> 
          		<div class="row">
          			<!-- Start product listing  -->
          			<%  Object[] products = (Object[]) request.getAttribute("productList");
			    		if(products.length > 0) {
							for (int i = 0; i < products.length; i++) {
								Product product = (Product)products[i]; %>
            		<div class="col-md-3">
              			<div class="card mb-4 box-shadow">
              			<form method="post" action="cart">
              				<input type="hidden" name="id" value="<%=product.getId()%>">
              				<div class="card-image-div">
              					<img class="card-img-top-width" src="<%=request.getContextPath()%>/upload/<%=product.getImage()%>" alt="<%=product.getName()%>">
              				</div>
                			<div class="card-body">
                				<h4><%=product.getName()%></h4>
               					<p class="card-text"><%=product.getDescription()%></p>
               					<div class="number" style="margin-bottom: 10px;">
									<span class="minus" title="Minus" data-id="<%=product.getId()%>">-</span>
									<input type="text" name="quantity" value="1" class="quantity" id="<%=product.getId()%>"/>
									<span class="plus" title="Plus" data-id="<%=product.getId()%>">+</span>
								</div>
               					<div class="d-flex justify-content-between align-items-center">
                    				<div class="btn-group">
                    					<button type="submit" class="btn btn-outline-secondary" title="Add to cart">Add to cart</button>
                    				</div>
                    				<small class="text-muted">$<%=(double)product.getPrice()%></small>
                  				</div>
                			</div>
               			</form>
              			</div>
            		</div>
            		<% } } %>
            		<!-- End product listing -->
            		<!-- Start model for check order -->
					<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  	<div class="modal-dialog modal-dialog-new-width	" role="document">
				    		<div class="modal-content">
					      		<div class="modal-header">
					        		<div><h5 class="modal-title" id="exampleModalLabel" style="line-height: 1; margin-right: 15px;">Order cart </h5></div>
					        		<div>
				        				<% if(cart != null) { %> <span>Total: $</span><%=cart.getTotal() %><span> <% } %>
					        		</div>
					        		<%-- { <span>Total: $</span><span><%= (cart != null) ? cart.getTotal() : 0.0%></span> }  --%>
					        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          			<span aria-hidden="true">&times;</span>
					        		</button>
				      			</div>
					      		<div class="modal-body">
				        			<div class="order-cart-list">
				        			<form method="post" action="update-cart">
					        			<table class="table">
					        				<thead>
					        					<tr>
					        						<th>Image</th>
					        						<th>Name</th>
					        						<th>Price</th>
					        						<th>Quantity</th>
					        						<th>Total</th>
					        						<th>Remove</th>
					        					</tr>
					        				</thead>
					        				<tbody>
					        				<%  if(cart != null) { 
					        						for(int i=0; i < cart.getCartItems().size(); i++) { 
					        							CartItem cartItem = cart.getCartItems().get(i);
					        							Product product = cartItem.getProduct();
					        				%>			
					        					<tr id="<%=product.getId()%>">
					        						<input type="hidden" name="product_id" value="<%=product.getId()%>">
					        						<td width="10%">
					        							<img width="100%" alt="<%=product.getName() %>" src="<%=request.getContextPath()%>/upload/<%=product.getImage()%>">
					        						</td>
					        						<td><%=product.getName()%></td>
					        						<td>$<%=product.getPrice()%></td>
					        						<td>
					        							<input type="number" id="cart_product_<%=product.getId()%>" name="quantity" value="<%= cartItem.getQuantity()%>" style="width: 40%" autocomplete="false">
					        						</td>
					        						<td>$<%=cartItem.getLineTotal()%></td>
					        						<td>
					        							<a href="cart?action=remove&id=<%=product.getId()%>" title="Remove"><i class="fa fa-trash"></i></a>
					        						</td>
					        					</tr>
					        				<%	} } else { %>
					        					<tr><p>No data found</p></tr>
					        				<% } %>
					        				</tbody>
					        			</table>
					        			<div>
					        				<a href="order" class="btn btn-primary mt-3" id="place_order_cart">Place order</a>
					        				<input type="submit" class="btn btn-warning mt-3" id="update_cart_btn" value="Update cart" style="display: none;">
					        			</div>
				        			</form>
				        			</div>
					      		</div>
					    	</div>
					  	</div>
					</div>
          			<!-- End model for check order -->
          		</div>
       		</div>
      	</div>
    </main>
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $('#list_product').DataTable();
    
} );
</script>
    <script type="text/javascript">
    	$(document).ready(function(){
    		var counter = 0;
    		$('.plus').on('click', function(){
    			var id = $(this).data("id");
    			var quantity = $("#"+id).val();
    			var updateQuantity = parseInt(quantity) + 1;
    			$("#"+id).val(updateQuantity);
    		});
    		
    		$('.minus').on('click', function(){
    			var id = $(this).data("id");
    			var quantity = $("#"+id).val();
    			var updateQuantity = parseInt(quantity) - 1;
    			if(updateQuantity != 0) { 
    				$("#"+id).val(updateQuantity); 
    			} else {
    				$("#"+id).val(1);
    			}
    		});
    		$("input[name=quantity]").on('change', function(){
    			var cart_product_id = $(this).attr("id");
    			var quantity = $("#"+cart_product_id).val();
    			if($("#"+cart_product_id).val() == null || $("#"+cart_product_id).val() == '' || $("#"+cart_product_id).val() == 0){
    				$("#"+cart_product_id).val(1)
    			}
    			$("#update_cart_btn").show();
    			$("#place_order_cart").hide();
    		});
    	});
    	
    	function search(text){
    		$.ajax({
    			url: "./search",
    			type: "post",
    			data : { search : text },
    			dataType: "json",
    			success: function(res){
    				/* alert("calling"); */
    			}
    		});
    	}
    </script>
  </body>
</html>
    