<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	//한글처리
	request.setCharacterEncoding("UTF-8");
%>

<%--컨텍스트 주소 얻기 --%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<html>
<head>
<title>1:1문의 수정</title>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/style.css" />

<script src="../js/jquery-3.4.1.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
</head>
<body>

	<section class="container body-container py-5">
		<div class="row">
			<div class="col-12">
				<h2>1:1문의 수정</h2>
			</div>
		</div>
		<!-- 게시판 -->
		<article class="mt-3">
			<form
				action="${contextPath}/qboard/qnaUpdate.do?qna_num=${qnaUpdate.qna_num}"
				method="post">
				<table class="table">
					<colgroup>
						<col style="max-width: 15%" />
						<col />
					</colgroup>
					<tr>
						<th class="align-middle"><label for="poolName" class="m-0">카테고리
								선택</label></th>
						<td>
							<!-- 						<input class="form-control" type="text" name="poolName" id="poolName" required /> -->
							<input type="radio" name="cate" value="티켓예매" checked="checked">티켓예매
							&nbsp;&nbsp; <input type="radio" name="cate" value="취소/환불">취소/환불
							&nbsp;&nbsp; <input type="radio" name="cate" value="이벤트">이벤트
							&nbsp;&nbsp; <input type="radio" name="cate" value="티켓수령">티켓수령
							&nbsp;&nbsp; <input type="radio" name="cate" value="회원">회원
							&nbsp;&nbsp;

						</td>
					</tr>

					<tr>
						<th class="align-middle"><label for="poolAddress1"
							class="m-0">제목</label></th>
						<td><input type="text" class="form-control" name="title"
							value="${qnaUpdate.qna_title}" id="poolAddress1" maxlength="50">
						</td>
					</tr>

					<tr>
						<th class="align-middle"><label for="contents" class="m-0">내용</label>
						</th>
						<td><textarea class="form-control" name="contents"
								id="poolContent" cols="40" rows="13" required>${qnaUpdate.qna_contents}</textarea>
						</td>
					</tr>


<!-- ID가 admin(관리자일때)만 문의답변란 보임 -->
<c:set var="id" value="admin"/>
<c:if test="${id == 'admin'}">

					<th class="align-middle"><label for="contents" class="m-0">답변</label>
					</th>
					<td>
						<textarea class="form-control" name="answer" id="poolContent" cols="40" rows="8" required
								  placeholder="-- 문의하신 내용에 대한 2Ticket 답변입니다 --">${qnaUpdate.answer}</textarea>
					</td>
					<tr>
					<th class="align-middle">
					<label for="poolName" class="m-0">카테고리선택</label>
					</th>
					<td><input type="radio" name="status" value="1"> 답변완료 &nbsp;&nbsp; 
						<input type="radio" name="status" value="0"	checked="checked"> 대기중 &nbsp;&nbsp;</td>
					</tr> 
</c:if>	
					
					
					<!-- 					
					<tr>
						<th class="align-middle">
							<label for="boardFile1" class="m-0">이미지 첨부1</label>
						</th>
						<td>
							<div class="custom-file">
								<input class="custom-file-input" type="file" name="boardFile1" id="boardFile1" onchange="readURL(this, 'image')" />
								<label class="custom-file-label" for="boardFile1">Choose file</label>
							</div>
						</td>
					</tr>
					 -->


				</table>
				<div class="text-center my-5">
					<button type="button" class="btn btn-secondary"
						onclick="history.back()">취소</button>
					<button type="submit" class="btn btn-primary">등록하기</button>
				</div>
			</form>
		</article>
		<!-- 게시판 -->
	</section>



	<%-- 	<script src="${contextPath}/js/bs-custom-file-input.js"></script>
	<script>
		$(document).ready(function() {
			bsCustomFileInput.init()
		})
		
		function readURL(obj, allowType){
			var $preview  = $(obj).parent().siblings(".preview");

			if($preview.length){
				$preview.remove();
			}
			
			if(obj.files && obj.files[0]){
				var fileType = obj.files[0].type.split("/")[0];
				
				if(fileType=="image"){
					$preview = $("<div class='preview' />");
					$preview.appendTo($(obj).parent().parent());
					
					var reader = new FileReader();				
					reader.readAsDataURL(obj.files[0]);
					
					reader.onload = function(ProgressEvent){
						$preview.css("background-image", "url(" + ProgressEvent.target.result + ")");
					}
				}else{
					if(allowType=="image"){
						alert("이미지 파일만 첨부하실 수 있습니다.");
						obj.value = "";
					}
				}
			}
		}
	</script>
	
	 --%>




</body>
</html>