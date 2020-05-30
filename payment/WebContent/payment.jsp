<%@page import="com.payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Items Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script type="text/javascript" src="Components/jquery-3.5.1.js"></script>
<script type="text/javascript" src="Components/payment.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payment Management</h1>
				<form id="formPay" name="formPay">
				
					Payment No: <input id="Payno" name="Payno" type="text" class="form-control form-control-sm"> <br> 
						
					Appointment ID: <input id="AppointID" name="AppointID" type="text" class="form-control form-control-sm"> <br> 
						
					Amount: <input id="Amount" name="Amount" type="text" class="form-control form-control-sm"> <br> 
					
					Patient Name: <input id="PatientName" name="PatientName" type="text" class="form-control form-control-sm"> <br>
						
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
						
					<input type="hidden" id="hidPayIDSave" name="hidPayIDSave" value="">
				
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				
				<div id="alertError" class="alert alert-danger"></div><br>
				
				<div id="divItemsGrid">
					<%
					payment pay = new payment();
					out.print(pay.readPayment());
					%>
				</div>
				
			</div>
		</div>
	</div>
</body>
</html>