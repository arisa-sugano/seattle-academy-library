<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
  
<meta charset="UTF-8">
  
<title>書籍検索結果｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>   
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
  
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
  
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
  
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
  
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
  
<script src="resources/js/thumbnail.js"></script>
    
<script src="resources/js/addBtn.js"></script>
</head>
<body class="wrapper">
      
    <header>
            
        <div class="left">
                  <img class="mark" src="resources/img/logo.png" />       
            <div class="logo">Seattle Library</div>
                
        </div>
            
        <div class="right">
                  
            <ul>
                        
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>         
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>       
            </ul>
                
        </div>
          
    </header>
     <main>
        <h1>書籍検索結果</h1>
         <div>
        <br>
        <form id = "searchBooks" action="<%=request.getContextPath()%>searchtBooks" method="POST">
        <br>
     
        <p>検索したいタイトルを入力してください。</p>
            <div class="choice">
           <input type ="radio" name  ="search" value="完全一致"  class="choice">完全一致
        <input type ="radio" name  ="search" value="部分一致" class="choice">部分一致</div>
        <br>
         <input id= "search" type="search" name="search" placeholder="タイトルを入力">
          <a href="<%=request.getContextPath()%>/searchBooks" class="fas fa-search">検索</a>
        </form>
        </div>
        <br>
        <span>の検索結果</span>
            <div>
        <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
     

                <div class="booklist">
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                            <form method="post" class="book_thumnail" action="<%=request.getContextPath()%>/details">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> 
                                <c:if test="${bookInfo.thumbnail =='null'}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${bookInfo.thumbnail !='null'}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <div class=.br>
                            <ul>                        
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">${bookInfo.author}(著)</li>
                                <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                                <li class="book_publisher">出版日：${bookInfo.publishDate}</li>
                            </ul>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
    
    <main>
     <h1>書籍検索結果</h1>
        <div>
        <br>
        <form id = "searchBooks" action="cgi-bin/example.cgi" method="POST">
        <br>
        
        <p>検索したいタイトルを入力してください。</p>
         <input id= "search1" type="search" name="search" placeholder="タイトルを入力">
          <a href="<%=request.getContextPath()%>/searchBooks" class="fas fa-search">検索</a>
        </form>
        </div>
        <br>
        <span>の検索結果</span>
                        <c:if test="${!empty bookInfo}">
                            <input type="text"  name="title" value="${bookInfo.title}">
                        </c:if>
                        <c:if test="${empty bookInfo}">
                            <input type="text" required name="title" autocomplete="off">
                        </c:if>
                    </div>
           
     <br>
            <div>
        <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
     

                <div class="booklist">
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                            <form method="post" class="book_thumnail" action="<%=request.getContextPath()%>/details">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> 
                                <c:if test="${bookInfo.thumbnail =='null'}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${bookInfo.thumbnail !='null'}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <div class=.br>
                            <ul>                        
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">${bookInfo.author}(著)</li>
                                <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                                <li class="book_publisher">出版日：${bookInfo.publishDate}</li>
                            </ul>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
    