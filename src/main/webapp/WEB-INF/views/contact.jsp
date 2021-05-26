
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">

<title>お問い合わせ画面｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>

<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet"
	type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP"
	rel="stylesheet">

<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css"
	rel="stylesheet">
<link href="<c:url value="/resources/css/contact.css" />"rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Corben:700 rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
	<header>
		<div class="left">
			      <img class="mark" src="resources/img/logo.png" />       
			<div class="logo">Seattle Library</div>
		</div>
		<h2>Contact us</h2>
		<div class="right">
			<ul>
				<li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
				<li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
			</ul>
		</div>
	</header>

	<div id="form">

		<div class="fish" id="fish"></div>
		<div class="fish" id="fish2"></div>

		<main>
			<form action="<%=request.getContextPath()%>/contactSystem"
				method="POST" id="waterform" enctype="multipart/form-data"
				id="data_upload_form">

				<div class="formgroup" id="name-form">
					<label for="name">Your name*</label> <input type="text" id="name"
						required name="inquirer" />
				</div>

				<div class="formgroup" id="email-form">
					<label for="email">Your e-mail*</label> <input type="email"
						id="email" required name="email" />
				</div>

				<div class="formgroup" id="message-form">
					<label for="message">Your message</label>
					<br>
					<textarea id="message" required name="content"></textarea>
				</div>
				<input type="submit" value="Send your message!" />
				<br>
				<c:if test="${!empty okMessage}">
                        <div class="error_msg">${okMessage}</div>
                </c:if>
			</form>
	</div>
	</main>
</body>
</html>