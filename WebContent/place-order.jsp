<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.cartapp.model.User" %>
<%@ page import="com.cartapp.model.Product" %>
<%@ page import="com.cartapp.model.Cart" %>
<%@ page import="com.cartapp.model.CartItem" %>
<%@ page import="com.cartapp.model.SessionCounter" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Place order</title>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
	<link rel="" href="style.css">
</head>
<style>
	html,body {
  		overflow-x: hidden; /* Prevent scroll on narrow devices */
	}
	body {
	  padding-top: 56px;
	}
	
	@media (max-width: 767.98px) {
	  .offcanvas-collapse {
	    position: fixed;
	    top: 56px; /* Height of navbar */
	    bottom: 0;
	    width: 100%;
	    padding-right: 1rem;
	    padding-left: 1rem;
	    overflow-y: auto;
	    background-color: var(--gray-dark);
	    transition: -webkit-transform .3s ease-in-out;
	    transition: transform .3s ease-in-out;
	    transition: transform .3s ease-in-out, -webkit-transform .3s ease-in-out;
	    -webkit-transform: translateX(100%);
	    transform: translateX(100%);
	  }
	  .offcanvas-collapse.open {
	    -webkit-transform: translateX(-1rem);
	    transform: translateX(-1rem); /* Account for horizontal padding on navbar */
	  }
	}

	.nav-scroller {
	  position: relative;
	  z-index: 2;
	  height: 2.75rem;
	  overflow-y: hidden;
	}
	
	.nav-scroller .nav {
	  display: -webkit-box;
	  display: -ms-flexbox;
	  display: flex;
	  -ms-flex-wrap: nowrap;
	  flex-wrap: nowrap;
	  padding-bottom: 1rem;
	  margin-top: -1px;
	  overflow-x: auto;
	  color: rgba(255, 255, 255, .75);
	  text-align: center;
	  white-space: nowrap;
	  -webkit-overflow-scrolling: touch;
	}
	
	.nav-underline .nav-link {
	  padding-top: .75rem;
	  padding-bottom: .75rem;
	  font-size: .875rem;
	  color: var(--secondary);
	}
	
	.nav-underline .nav-link:hover {
	  color: var(--blue);
	}

	.nav-underline .active {
	  font-weight: 500;
	  color: var(--gray-dark);
	}
	
	.text-white-50 { color: rgba(255, 255, 255, .5); }
	.bg-purple { background-color: var(--purple); }
	.border-bottom { border-bottom: 1px solid #e5e5e5; }
	.box-shadow { box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05); }
	.lh-100 { line-height: 1; }
	.lh-125 { line-height: 1.25; }
	.lh-150 { line-height: 1.5; }
	.card-body{
		padding: 0px 15px !important;
	}
	.text-dark{
		font-size: 20px !important;
	}
	.card-text{
	font-size: 16px;
	line-height: 1.3;}
	.card-text span{
	font-size: 14px;}
	img{
	width: 100px; height: 100px;}
	.is-required{
	color: #dc3545;}
</style>
<body>
	<% Cart cart = (Cart) session.getAttribute("cart"); %>
    <% User userData = (User)session.getAttribute("user");  if(userData != null) { %>
	<div class="container">
		<div class="d-flex mb-4">
			<p style="margin-bottom: 0; line-height: 2.5">Welcome, <%=userData.getName() %></p>
			<a href="logout" class="btn btn-danger ml-3" title="">Logout</a>
		</div>
		<div><p><a href="product">Continue shopping</a></p></div> 
		<div> Current active users : <%=SessionCounter.getSessionCount() %></div> 
		<h3 class="text-center mb-4">Place order</h3>
		<div class="row">
        	<form method="post" id="place_order" style="width: 100%;">
	        	<div class="mx-auto col-md-8">
	        		<%  if(cart != null) { 
							for(int i=0; i < cart.getCartItems().size(); i++) { 
								CartItem cartItem = cart.getCartItems().get(i);
								Product product = cartItem.getProduct();
					%>
	          		<div class="card flex-md-row mb-4 box-shadow h-md-250" id="">
	          			<input type="hidden" name="id" value="<%=product.getId()%>">
	          			<input type="hidden" name="price" value="<%=product.getPrice()%>">
	          			<input type="hidden" name="quantity" value="<%=cartItem.getQuantity()%>">
	          			<input type="hidden" name="row_total" value="<%=cartItem.getLineTotal()%>">
	          			<div>
							<img class="card-img-left flex-auto d-none d-md-block" style="padding: 15px;	" src="<%=request.getContextPath()%>/upload/<%=product.getImage()%>" alt="<%=product.getName() %>">
	            		</div>
	            		<div class="card-body d-flex flex-column align-items-start" style="padding-top: 10px !important;">
	              			<h3 class="mb-2" style="line-height: 0.8;">
	              			</h3>
	              			<p class="card-text" style="margin-bottom: 5px;"><%=product.getDescription()%></p>
	              			<p class="card-text">
	              				<span>Price: </span><span>$<%=product.getPrice()%></span> | <span>Quantity: </span><span><%=cartItem.getQuantity()%></span>  | <span>Amount: </span><span>$<%=cartItem.getLineTotal()%></span>
	              			</p>
	            		</div>
	          		</div>
	          		<% } } %>
	        	</div>
	        	<div class="mx-auto col-md-8">
	        		<div class="form-group">
	        			<label>Total cost ( $ )</label>
	        			<input type="text" name="total" value="<%= (cart != null) ? cart.getTotal() : 0.0%>" class="form-control" readonly="readonly">
	        		</div>
	        		<div class="form-group">
	        			<label>Address <span class="is-required">*</span></label>
	        			<textarea rows="3" class="form-control" name="address"></textarea>
	        		</div>
	        		<button type="submit" class="btn btn-success">Place order</button>
		        </div>
	      	</form>
		</div>	
	</div>
	<% } %>
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#place_order').validate({
			errorElement: 'div',
			errorClass: 'help-block',
			rules:{
				address: { required: true}
			},
			messages:{
				address: { required: "Please, enter your delivery address"}
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