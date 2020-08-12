<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transfer</title>
<style>
form{
	border:2px solid black;
	margin-top:10%;
	margin-left:31%;
	text-align:center;
	width:25%;
	padding:50px;
	border-radius:10px;
	background-image: linear-gradient(to right,#2193b0 , #6dd5ed);
}
input{
	border-radius:3px;
	border:2px solid black;
}
input:focus {
	border-color:lightblue;
}
</style>
</head>
<body>
<form action="balance" method="POST">
<input placeholder="Account number" type="number" name="acc_no" required/><br><br>
<input placeholder="Amount" type="number" name="amount" required/><br><br>
<input type="submit" value="TRANSFER"/>
</form>
</body>
</html>
