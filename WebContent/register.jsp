<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Register</title>
    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<style>
	html, body {
  		height: 100%;
	}
	body {
	  display: -ms-flexbox;
	  display: -webkit-box;
	  display: flex;
	  -ms-flex-align: center;
	  -ms-flex-pack: center;
	  -webkit-box-align: center;
	  align-items: center;
	  -webkit-box-pack: center;
	  justify-content: center;
	  padding-top: 40px;
	  padding-bottom: 40px;
	  background-color: #f5f5f5;
	}
	.form-signin {
	  width: 100%;
	  max-width: 330px;
	  padding: 15px;
	  margin: 0 auto;
	}
	.form-signin .checkbox {
	  font-weight: 400;
	}
	.form-signin .form-control {
	  position: relative;
	  box-sizing: border-box;
	  height: auto;
	  padding: 10px;
	  font-size: 16px;
	}
	.form-signin .form-control:focus {
	  z-index: 2;
	}
	.form-signin input[type="text"] {
	  margin-bottom: -1px;
	  border-bottom-right-radius: 0;
	  border-bottom-left-radius: 0;
	}
	.form-signin input[type="email"] {
	  margin-bottom: -1px;
	  border-radius: 0;
	}
	.form-signin input[type="password"] {
	  margin-bottom: 10px;
	  border-top-left-radius: 0;
	  border-top-right-radius: 0;
	}
	.is-required{
	color: #dc3545;}
</style>
<body class="text-center">
    <form id="register" class="form-signin" method="post" action="register">
      	<h1 class="h3 mb-3 font-weight-normal">Please register</h1>
      	<!-- Name -->
      	<label for="name" class="sr-only">Name <span class="is-required">*</span></label>
      	<input type="text" id="name" name="name" class="form-control" placeholder="Name" autofocus>
      	
      	<!-- Email -->
      	<label for="inputEmail" class="sr-only">Email address <span class="is-required">*</span></label>
      	<input type="email" id="inputEmail" name="email" class="form-control" placeholder="Email address" autofocus>
      	
      	<!-- Password -->
      	<label for="inputPassword" class="sr-only">Password <span class="is-required">*</span></label>
      	<input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password">
      	
      	<!-- Submit button -->
      	<button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
      	<p style="margin-top: 15px; margin-bottom: 5px;">
      		<a href="register">Sign in</a>
      	</p>
      	<p class="mt-5 mb-3 text-muted">&copy; 2017-2019</p>
    </form>
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.1/dist/jquery.validate.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#registe').validate({
			errorElement: 'div',
			errorClass: 'help-block',
			rules:{
				name: { required: true},
				email: { required: true },
				password: { required: true }
			},
			messages:{
				name: { required: "Please, enter name"},
				email: {required: "Please, enter email"},
				password: {required: "Please, enter password"}
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
