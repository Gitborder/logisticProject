<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>JSP Board</title>

<!-- 게시글 입력창 디자인 설정 -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://malsup.github.io/jquery.form.js"></script> 

<script>

$(document).ready(function() {
    
    var todayTime = new Date();         
    var rrrr = todayTime.getFullYear();
    var mm = todayTime.getMonth()+1;
    var dd = todayTime.getDate();
    var today = rrrr+"-"+addZeros(mm,2)+"-"+addZeros(dd,2);
    console.log(today);
    $("#date").val(today);
    
    $("#registBoard").button().click(function(){
       $("#regist_board").submit();


       location.href='${pageContext.request.contextPath}/board/board.html';
    });
    
   $("#regist_board").ajaxForm({
       dataType: "json",
       success: function(data){ 
          alert(data.errorMsg);
          location.href = "${pageContext.request.contextPath}/board/board.html";
       }
    });     
 });

	   function addZeros(num, digit) {           
	      var zero = '';
	       num = num.toString();
	       if (num.length < digit) {
	          for (i = 0; i < digit - num.length; i++) {
	           zero += '0';
	          }
	       }
	       return zero + num;
	   }

</script>
</head>
<body >
<center>
 <form id="regist_board" target="iframe" action="${pageContext.request.contextPath}/base/registBoard" method="POST" enctype="multipart/form-data" >
<h2> 게시글 작성 </h2> 
 
<form action="BoardWriteProc.jsp" method="post">
  <fieldset>
<table width = "600" border="1" bordercolor = "gray" bgcolor = "#F9F9F9" class="table"> <!-- bordercolor는 선색깔 지정 -->
      <tr height = "40">
        <td align = "center" width = "150"> 작성자 </td>
            <td width = "450"> <input type="text" name="reg_date" style="width:137px;" id="date" value="${today}"></td>        
    </tr>
   
    <tr height = "40">
        <td align = "center" width = "150"> 작성자 </td> <!-- ref는 데이터베이스에서 처리하기 때문에 따로 받지 않는다. -->
        <td width = "450"> <input type = "text" name = "name" size = "60" value="${sessionScope.empName}"></td> 
    </tr>
    
    <tr height = "40">
        <td align = "center" width = "150"> 제목 </td>
        <td width = "450"> <input type = "text" name = "subject" size = "60" id="titleBoard"></td>
    </tr>
   
    <tr height = "40">
        <td align = "center" width = "150"> 글내용 </td>
        <td width = "450"><textarea rows = "10" cols = "120" name = "content" id="ontentBoard"></textarea></td>
    </tr>
    <tr height = "40">
       <td align = "center" width = "150">파일 찾기</td>
       <td width = "450"><input type="file" name="filename" size="50" maxlength="50" id="filedBoard"></td>
     </tr>
    
    <tr height = "40">
        <td align = "center" colspan = "2">
             <input type="button" value="등록" id="registBoard" />
              <input type="button" value="취소" onclick="location.href='${pageContext.request.contextPath}/board/board.html'" /> &nbsp;&nbsp;   
            <input type = "reset" value = "다시작성">       
        </td>
    </tr>
</table>
<input type="hidden" name="pboard_seq" value="${param.board_seq}">
</fieldset>
</form>
</center>
</body>
</html>
